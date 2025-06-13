package com.coderush.service;

import com.coderush.dto.request.problem.BugFixSubmissionDTO;
import com.coderush.dto.request.problem.KnowledgeSubmissionDTO;
import com.coderush.dto.response.SubmissionDTO;

import java.util.List;

public interface SubmissionService {
    boolean submitBugFixAnswer(BugFixSubmissionDTO dto, Long userId);

    boolean submitKnowledgeProblem(KnowledgeSubmissionDTO dto, Long userId);

    List<SubmissionDTO> getSubmissionsByUser(Long userId);
}
