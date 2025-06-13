package com.coderush.service;

import com.coderush.domain.entity.Submission;
import com.coderush.domain.entity.User;
import com.coderush.domain.enums.ProblemType;
import com.coderush.dto.request.problem.BugFixSubmissionDTO;
import com.coderush.dto.request.problem.KnowledgeSubmissionDTO;
import com.coderush.dto.response.SubmissionDTO;
import com.coderush.repository.BugFixProblemRepository;
import com.coderush.repository.KnowledgeProblemRepository;
import com.coderush.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {
    private final BugFixProblemRepository bugFixProblemRepository;
    private final KnowledgeProblemRepository knowledgeProblemRepository;
    private final SubmissionRepository submissionRepository;

    @Override
    public boolean submitBugFixAnswer(BugFixSubmissionDTO dto, Long userId) {
        var problem = bugFixProblemRepository.findById(dto.getProblemId())
                .orElseThrow(() -> new IllegalArgumentException("해당 버그 문제가 존재하지 않습니다."));

        boolean isCorrect;
        String submitted;

        if (problem.getType().isObjective()) {
            isCorrect = problem.getAnswer().equals(dto.getSelectedChoice());
            submitted = dto.getSelectedChoice();
        }
        else {
            isCorrect = problem.getTargetSnippet().equals(dto.getTargetSnippet()) &&
                    problem.getCorrectFix().equals(dto.getFixAttempt());

            submitted = "Target: " + dto.getTargetSnippet() + ", Fix: " + dto.getFixAttempt();
        }

        submissionRepository.save(
                Submission.builder()
                        .problemId(problem.getId())
                        .problemType(ProblemType.BUG_FIX)
                        .submittedAnswer(submitted)
                        .correct(isCorrect)
                        .user(User.builder().id(userId).build())
                        .build()
        );

        return isCorrect;
    }

    @Override
    public boolean submitKnowledgeProblem(KnowledgeSubmissionDTO dto, Long userId) {
        var problem = knowledgeProblemRepository.findById(dto.getProblemId())
                .orElseThrow(() -> new IllegalArgumentException("해당 지식 문제가 존재하지 않습니다."));

        boolean isCorrect;
        String submitted;

        if (problem.getType().isObjective()) {
            isCorrect = problem.getAnswer().equals(dto.getSelectedChoice());
            submitted = dto.getSelectedChoice();
        }
        else {
            isCorrect = problem.getAnswer().equalsIgnoreCase(dto.getWrittenAnswer());
            submitted = dto.getWrittenAnswer();
        }

        submissionRepository.save(
                Submission.builder()
                        .problemId(problem.getId())
                        .problemType(ProblemType.KNOWLEDGE)
                        .submittedAnswer(submitted)
                        .correct(isCorrect)
                        .user(User.builder().id(userId).build())
                        .build()
        );

        return isCorrect;
    }

    @Override
    public List<SubmissionDTO> getSubmissionsByUser(Long userId) {
        return submissionRepository.findByUser_Id(userId).stream()
                .map(SubmissionDTO::from)
                .collect(toList());
    }
}
