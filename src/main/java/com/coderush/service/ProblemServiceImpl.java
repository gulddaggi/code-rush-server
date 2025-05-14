package com.coderush.service;

import com.coderush.domain.problem.BugFixProblemDTO;
import com.coderush.domain.problem.KnowledgeProblemDTO;
import com.coderush.domain.problem.ProblemDTO;
import com.coderush.repository.BugFixProblemRepository;
import com.coderush.repository.KnowledgeProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {
    private final BugFixProblemRepository bugFixProblemRepository;
    private final KnowledgeProblemRepository knowledgeProblemRepository;

    @Override
    public List<ProblemDTO> getAllProblems() {
        return List.of();
    }

    @Override
    public BugFixProblemDTO getBugFixProblemById(Long id) {
        return null;
    }

    @Override
    public KnowledgeProblemDTO getKnowledgeProblemById(Long id) {
        return null;
    }
}
