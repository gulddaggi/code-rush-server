package com.coderush.domain.problem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
