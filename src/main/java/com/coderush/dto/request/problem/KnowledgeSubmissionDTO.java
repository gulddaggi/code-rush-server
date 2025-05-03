package com.coderush.dto.request.problem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeSubmissionDTO {
    private Long problemId;
    // 선택한 객관식 번호
    private String selectedChoice;
    // 작성한 주관식 정답
    private String writtenAnswer;
}
