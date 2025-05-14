package com.coderush.domain.problem;

import com.coderush.domain.entity.KnowledgeProblem;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KnowledgeProblemDTO implements ProblemDTO {
    private Long id;
    private String title;
    private String description;
    private ProblemType type;

    // 객관식인 경우 사용
    List<String> choices;
    private String answer;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public ProblemType getType() {
        return type;
    }

    public static KnowledgeProblemDTO from(KnowledgeProblem entity) {
        return KnowledgeProblemDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .type(entity.getType())
                .choices(entity.getChoices())
                .answer(entity.getAnswer())
                .build();
    }
}
