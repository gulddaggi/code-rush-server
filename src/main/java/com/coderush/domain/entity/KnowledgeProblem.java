package com.coderush.domain.entity;

import com.coderush.domain.problem.ProblemType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "knowledge_problems")
public class KnowledgeProblem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private ProblemType type;

    private String selectedChoice;
    private String answer;
}
