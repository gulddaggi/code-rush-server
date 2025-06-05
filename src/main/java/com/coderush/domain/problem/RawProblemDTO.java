package com.coderush.domain.problem;

import com.coderush.domain.entity.BugFixProblem;
import com.coderush.domain.entity.KnowledgeProblem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RawProblemDTO {
    @JsonProperty("type")
    private ProblemCategory category;
    private String title;
    private String description;
    private List<String> choices;
    private String answer;
    private String targetSnippet;
    private String correctFix;

    public ProblemDTO toProblemDTO() {
        ProblemType format = (choices != null && !choices.isEmpty())
                ? ProblemType.OBJECTIVE : ProblemType.SUBJECTIVE;

        switch (category) {
            case BUGFIX -> {
                return BugFixProblemDTO.builder()
                        .category(category)
                        .type(format)
                        .title(title)
                        .description(description)
                        .choices(choices)
                        .answer(answer)
                        .targetSnippet(targetSnippet)
                        .correctFix(correctFix)
                        .build();
            }
            case KNOWLEDGE -> {
                return KnowledgeProblemDTO.builder()
                        .category(category)
                        .type(format)
                        .title(title)
                        .description(description)
                        .choices(choices)
                        .answer(answer)
                        .build();
            }
            default -> throw new IllegalStateException("Unknown category: " + category);
        }
    }
}
