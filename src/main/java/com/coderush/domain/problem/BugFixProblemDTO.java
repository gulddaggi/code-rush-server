package com.coderush.domain.problem;

import com.coderush.domain.entity.BugFixProblem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BugFixProblemDTO implements ProblemDTO {
    private Long id;
    private String title;
    private String decription;
    private ProblemType type;

    // 객관식
    private List<String> choices;
    private String answer;

    // 주관식
    private String targetSnippet;
    private String correctFix;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public ProblemType getType() {
        return type;
    }

    public static BugFixProblemDTO from(BugFixProblem entity) {
        return BugFixProblemDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .decription(entity.getDescription())
                .type(entity.getType())
                .choices(entity.getChoices())
                .answer(entity.getAnswer())
                .targetSnippet(entity.getTargetSnippet())
                .correctFix(entity.getCorrectFix())
                .build();
    }
}
