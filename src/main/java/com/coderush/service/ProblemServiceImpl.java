package com.coderush.service;

import com.coderush.domain.problem.BugFixProblemDTO;
import com.coderush.domain.problem.KnowledgeProblemDTO;
import com.coderush.domain.problem.ProblemDTO;
import com.coderush.repository.BugFixProblemRepository;
import com.coderush.repository.KnowledgeProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {
    private final BugFixProblemRepository bugFixProblemRepository;
    private final KnowledgeProblemRepository knowledgeProblemRepository;

    /**
     * 생성된 모든 문제(BugFix + Knowledge)를 통합 리스트로 반환
     *
     * @return 문제 목록 (ProblemDTO 다형성 반환)
     */
    @Override
    public List<ProblemDTO> getAllProblems() {
        List<ProblemDTO> problems = new ArrayList<>();

        problems.addAll(
                bugFixProblemRepository.findAll().stream()
                        .map(BugFixProblemDTO::from)
                        .toList()
        );

        problems.addAll(
                knowledgeProblemRepository.findAll().stream()
                        .map(KnowledgeProblemDTO::from)
                        .toList()
        );

        return problems;
    }

    /**
     * id에 해당되는 버그 수정 문제를 반환
     * @param id
     * @return 버그 수정 문제
     */
    @Override
    public BugFixProblemDTO getBugFixProblemById(Long id) {
        return bugFixProblemRepository.findById(id)
                .map(BugFixProblemDTO::from)
                .orElseThrow(() -> new IllegalArgumentException("해당 버그 수정 문제가 존재하지 않습니다. ID : " + id));
    }

    /**
     * id에 해당되는 지식 문제를 제공
     * @param id
     * @return 지식 풀이 문제
     */
    @Override
    public KnowledgeProblemDTO getKnowledgeProblemById(Long id) {
        return knowledgeProblemRepository.findById(id)
                .map(KnowledgeProblemDTO::from)
                .orElseThrow(() -> new IllegalArgumentException("해당 지식 문제가 존재하지 않습니다. ID : " + id));
    }
}
