package com.coderush.domain.problem;

import com.coderush.domain.entity.BugFixProblem;
import com.coderush.domain.entity.KnowledgeProblem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RawProblemDTO {
    private String type; // GPT가 주는 값: "bugfix" or "knowledge"
    private String title;
    private String description;
    private List<String> choices;
    private String answer;
    private String targetSnippet;
    private String correctFix;

    public ProblemDTO toProblemDTO() {
        ProblemType format = (choices != null && !choices.isEmpty())
                ? ProblemType.OBJECTIVE
                : ProblemType.SUBJECTIVE;

        if ("bugfix".equalsIgnoreCase(type)) {
            return BugFixProblemDTO.builder()
                    .title(title)
                    .description(description)
                    .type(format)
                    .choices(getChoices())
                    .answer(answer)
                    .targetSnippet(targetSnippet)
                    .correctFix(correctFix)
                    .build();
        } else {
            return KnowledgeProblemDTO.builder()
                    .title(title)
                    .description(description)
                    .type(format)
                    .choices(choices)
                    .answer(answer)
                    .build();
        }
    }

    public Object toEntity() {
        ProblemType format = (answer != null && !answer.isEmpty()) ? ProblemType.OBJECTIVE : ProblemType.SUBJECTIVE;

        if ("bugfix".equalsIgnoreCase(type)) {
            return BugFixProblem.builder()
                    .title(title)
                    .description(description)
                    .type(format)
                    .answer(answer)
                    .targetSnippet(targetSnippet)
                    .correctFix(correctFix)
                    .build();
        } else {
            return KnowledgeProblem.builder()
                    .title(title)
                    .description(description)
                    .type(format)
                    .answer(answer)
                    .build();
        }
    }

}
