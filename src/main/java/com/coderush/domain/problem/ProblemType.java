package com.coderush.domain.problem;

public enum ProblemType {
    OBJECTIVE,
    SUBJECTIVE;

    public boolean isObjective() {
        return this == OBJECTIVE;
    }
}
