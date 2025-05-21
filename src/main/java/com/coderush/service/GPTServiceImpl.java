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
                result.add(dto);

                Object entity = raw.toEntity();
                if (dto instanceof BugFixProblemDTO bugfixDTO) {
                    bugFixProblemRepository.save(bugfixDTO.toEntity());
                } else if (dto instanceof KnowledgeProblemDTO knowledgeDTO) {
                    knowledgeProblemRepository.save(knowledgeDTO.toEntity());
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
    - "description": a brief explanation of the problem (written in Korean)
    - "choices": a list of 4 options (include this field only for multiple-choice questions)
    - "answer": a **single string** that represents the correct answer

        ⚠️ The "answer" field must:
        - Contain **no more than 150 characters**
        - Be a **short explanation** or a **single line of code**
        - **Never** include full methods or class definitions

    - "targetSnippet": (bugfix only) buggy Java code that the user must fix
    - "correctFix": (bugfix only) the corrected Java code

    ✨ Notes:
    - At least 2 of the problems should be multiple-choice (include "choices")
    - All problem content (title, description, choices, answer) must be in **Korean**
    """;
    }

}
