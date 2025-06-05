package com.coderush.dto.response;

import com.coderush.domain.entity.Submission;
import com.coderush.domain.enums.ProblemType;
import com.coderush.domain.problem.ProblemCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubmissionDTO {
    private Long id;
    private Long problemId;
    private ProblemType problemType;
    private ProblemCategory category;
    private String submittedAnswer;
    private Boolean correct;
    private Long userId;

    public static SubmissionDTO from(Submission entity) {
        return SubmissionDTO.builder()
                .id(entity.getId())
                .problemId(entity.getProblemId())
                .problemType(entity.getProblemType())
                .category(entity.getCategory())
                .submittedAnswer(entity.getSubmittedAnswer())
                .correct(entity.getCorrect())
                .userId(entity.getUser().getId())
                .build();
    }
}
