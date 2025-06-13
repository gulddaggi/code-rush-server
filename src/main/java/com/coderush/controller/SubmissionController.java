package com.coderush.controller;


import com.coderush.dto.request.problem.BugFixSubmissionDTO;
import com.coderush.dto.request.problem.KnowledgeSubmissionDTO;
import com.coderush.dto.response.SubmissionDTO;
import com.coderush.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submit")
@RequiredArgsConstructor
public class SubmissionController {
    private final SubmissionService submissionService;

    // TODO: 인증 붙이기 전까지는 userId를 외부에서 전달받는 방식으로 처리

    /**
     * 버그 문제 제출 처리
     */
    @PostMapping("/bugfix")
    public ResponseEntity<Boolean> submitBugFix(@RequestBody BugFixSubmissionDTO dto, @RequestParam Long userId) {
        boolean isCorrect = submissionService.submitBugFixAnswer(dto, userId);
        return ResponseEntity.ok(isCorrect);
    }

    /**
     * 지식 문제 제출 처리
     */
    @PostMapping("/knowledge")
    public ResponseEntity<Boolean> submitKnowledge(@RequestBody KnowledgeSubmissionDTO dto, @RequestParam Long userId) {
        boolean isCorrect = submissionService.submitKnowledgeProblem(dto, userId);
        return ResponseEntity.ok(isCorrect);
    }

    /**
     *  체점 결과 조회
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SubmissionDTO>> getUserSubmissions(@PathVariable Long userId) {
        return ResponseEntity.ok(submissionService.getSubmissionsByUser(userId));
    }
}
