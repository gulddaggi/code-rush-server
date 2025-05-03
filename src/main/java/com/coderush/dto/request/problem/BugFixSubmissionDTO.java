package com.coderush.dto.request.problem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BugFixSubmissionDTO {
    private Long problemId;
    // 선택한 객관식 번호
    private String selectedChoice;
    // 주관식 수정 대상
    private String targetSnippet;
    // 주관식 수정 내용
    private String fixAttempt;
}
