package com.coderush.service;

import com.coderush.domain.problem.ProblemDTO;

import java.util.List;

public interface GPTService {
    List<ProblemDTO> generateProblemSet();
}
