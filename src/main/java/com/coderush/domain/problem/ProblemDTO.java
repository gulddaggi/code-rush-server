package com.coderush.domain.problem;

public interface ProblemDTO {
    Long getId();
    String getTitle();
    ProblemType getType();
    ProblemCategory getCategory();
}
