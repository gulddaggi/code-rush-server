package com.coderush.service;

import com.coderush.domain.problem.BugFixProblemDTO;
import com.coderush.domain.problem.KnowledgeProblemDTO;
import com.coderush.domain.problem.ProblemDTO;

import java.util.List;

public interface ProblemService {
    List<ProblemDTO> getAllProblems();

    BugFixProblemDTO getBugFixProblemById(Long id);

    KnowledgeProblemDTO getKnowledgeProblemById(Long id);
}
