package com.coderush.domain.problem;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ProblemCategory {
    BUGFIX, KNOWLEDGE;

    @JsonCreator
    public static ProblemCategory from(String value) {
        return value != null ? ProblemCategory.valueOf(value.toUpperCase()) : null;
    }
}
