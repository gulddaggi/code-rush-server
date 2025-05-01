package com.coderush.domain.problem;

import java.util.List;

public class KnowledgeProblemDTO implements ProblemDTO {
    private Long id;
    private String title;
    private String decription;
    private ProblemType type;

    // 객관식인 경우 사용
    List<String> choices;
    private String answer;

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
