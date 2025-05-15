package com.coderush.dto.response;

import com.coderush.domain.entity.Submission;
import com.coderush.domain.enums.ProblemType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubmissionDTO {
    private Long id;
    private Long problemId;
    private ProblemType problemType;
    private String submittedAnswer;
    private Boolean correct;
    private Long userId;

    public static SubmissionDTO from(Submission entity) {
        return SubmissionDTO.builder()
                .id(entity.getId())
                .problemId(entity.getProblemId())
                .problemType(entity.getProblemType())
                .submittedAnswer(entity.getSubmittedAnswer())
                .correct(entity.getCorrect())
                .userId(entity.getUser().getId())
                .build();
    }
}
