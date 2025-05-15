package com.coderush.service;

import com.coderush.dto.request.problem.BugFixSubmissionDTO;
import com.coderush.dto.request.problem.KnowledgeSubmissionDTO;

public interface SubmissionService {
    void submitBugFixAnswer(BugFixSubmissionDTO dto, Long userId);

    void submitKnowledgeProblem(KnowledgeSubmissionDTO dto, Long userId);
}
