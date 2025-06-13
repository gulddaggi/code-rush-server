package com.coderush.service;

import com.coderush.domain.entity.BugFixProblem;
import com.coderush.domain.entity.KnowledgeProblem;
import com.coderush.domain.problem.BugFixProblemDTO;
import com.coderush.domain.problem.KnowledgeProblemDTO;
import com.coderush.domain.problem.ProblemDTO;
import com.coderush.domain.problem.RawProblemDTO;
import com.coderush.repository.BugFixProblemRepository;
import com.coderush.repository.KnowledgeProblemRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GPTServiceImpl implements GPTService {
    @Value("${openai.api.key}")
    private String apiKey;

    @PostConstruct
    public void testApiKey() {
        System.out.println("testApiKey" + apiKey);
    }

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final BugFixProblemRepository bugFixProblemRepository;
    private final KnowledgeProblemRepository knowledgeProblemRepository;

    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

    @Override
    public List<ProblemDTO> generateProblemSet() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "model", "gpt-4-1106-preview",
                "messages", List.of(
                        Map.of("role", "system", "content", "You are a helpful coding tutor."),
                        Map.of("role", "user", "content", getPromptText())
                )
        );

        HttpEntity<?> request = new HttpEntity<>(body, headers);
        String rawResponse = restTemplate.postForObject(OPENAI_URL, request, String.class);

        try {
            String json = objectMapper.readTree(rawResponse)
                    .get("choices").get(0).get("message").get("content").asText();

            if (json.startsWith("```json") || json.startsWith("```")) {
                json = json.replaceAll("(?s)```json\\s*", "")
                        .replaceAll("(?s)```\\s*", "")
                        .trim();
            }

            JsonNode parsed = objectMapper.readTree(json);
            List<ProblemDTO> result = new ArrayList<>();

            for (JsonNode node : parsed) {
                RawProblemDTO raw = objectMapper.treeToValue(node, RawProblemDTO.class);
                ProblemDTO dto = raw.toProblemDTO();

                if (dto instanceof BugFixProblemDTO bugfixDTO) {
                    BugFixProblem saved = bugFixProblemRepository.save(bugfixDTO.toEntity());
                    bugfixDTO.setId(saved.getId());
                    result.add(bugfixDTO);
                } else if (dto instanceof KnowledgeProblemDTO knowledgeDTO) {
                    KnowledgeProblem saved = knowledgeProblemRepository.save(knowledgeDTO.toEntity());
                    knowledgeDTO.setId(saved.getId());
                    result.add(knowledgeDTO);
                }
            }

            return result;
        } catch (Exception e) {
            System.err.println("GPT 응답 파싱 실패 원문:\n" + rawResponse);
            throw new RuntimeException("GPT 응답 파싱 실패", e);
        }
    }

    private String getPromptText() {
        return """
Please generate a total of 5 programming problems in a JSON array format:

- 3 Java bug-fix problems
- 2 computer science knowledge questions

❗ Output must be a pure JSON array. Do NOT include explanations, markdown, or code block markers.

Each object in the array must include the following fields:
- "type": either "bugfix" or "knowledge"
- "title": the title of the problem (written in Korean)
- "description": 
    - For "bugfix": MUST follow this EXACT format:
      - First line(s): problem explanation in Korean.
      - Then a line: "코드:" 
      - Then the buggy Java code (must be at least **6~10 lines long**, valid but buggy Java code).
      - Example format:
        문제 설명 텍스트 ...
        
        코드:
        public int exampleMethod() {
            ...
        }
    - For "knowledge": simple explanation only.
- "choices": a list of 4 options (include this field only for multiple-choice questions)
- "answer": 
    - For "knowledge": exactly one choice text (must match one of the "choices")
    - For "bugfix": the corrected version of "targetSnippet" — EXACTLY ONE line of code — with no comments or extra text.

⚠️ The "answer" field must:
- Contain **no more than 150 characters**
- For "bugfix": be EXACTLY ONE line of code that replaces "targetSnippet"
- For "knowledge": one choice text exactly

- "targetSnippet": (bugfix only) EXACTLY ONE buggy line from the buggy code that must be fixed.
- "correctFix": (bugfix only) the corrected full Java method code — only the method, no surrounding classes or comments.

✨ Notes:
- At least 2 of the problems should be multiple-choice (include "choices")
- All problem content (title, description, choices, answer) must be in **Korean**
- For bugfix problems:
  - "description" must follow the specified format with "코드:" line clearly separating explanation and code.
  - "targetSnippet" must be exactly ONE buggy line from the buggy code
  - "correctFix" must be the fixed full method code
  - "answer" must be the corrected version of "targetSnippet" (one line)
  - The buggy code should NOT be trivial (avoid basic off-by-one or null checks). Prefer bugs involving logic errors, incorrect API usage, wrong control flow, wrong variable usage, etc.
""";
    }
}
