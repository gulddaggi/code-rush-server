package com.coderush.controller;

import com.coderush.domain.problem.BugFixProblemDTO;
import com.coderush.domain.problem.KnowledgeProblemDTO;
import com.coderush.domain.problem.ProblemDTO;
import com.coderush.service.GPTService;
import com.coderush.service.ProblemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/problems")
@RequiredArgsConstructor
public class ProblemController {
    private final ProblemService problemService;
    private final GPTService gptService;

    /**
     * 전체 문제 목록 조회
     */
    @GetMapping
    public ResponseEntity<List<ProblemDTO>> getAllProblems() {
        return ResponseEntity.ok(problemService.getAllProblems());
    }

    /**
     * 버그 수정 문제 단건 조회
     */
    @GetMapping("/bugfix/{id}")
    public ResponseEntity<BugFixProblemDTO> getBugFIxProblem(@PathVariable Long id) {
        return ResponseEntity.ok(problemService.getBugFixProblemById(id));
    }

    /**
     * 지식 문제 단건 조회
     */
     @GetMapping("/knowledge/{id}")
    public ResponseEntity<KnowledgeProblemDTO> getKnowledgeProblem(@PathVariable Long id) {
         return ResponseEntity.ok(problemService.getKnowledgeProblemById(id));
     }

    /**
     * 문제 출제
     */
    @GetMapping(value = "/set", produces = "application/json")
    public ResponseEntity<String> getSetProblems() throws JsonProcessingException {


        List<ProblemDTO> problems = gptService.generateProblemSet();

        // JSON 문자열로 직렬화
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(problems);

        // 헤더에 Content-Length 수동 삽입
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentLength(json.getBytes(StandardCharsets.UTF_8).length);

        return new ResponseEntity<>(json, headers, HttpStatus.OK);    }
}
