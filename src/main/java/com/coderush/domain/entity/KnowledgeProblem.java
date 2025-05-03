package com.coderush.domain.entity;

import com.coderush.domain.problem.ProblemType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "knowledge_problems")
@Data
@NoArgsConstructor
public class KnowledgeProblem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private ProblemType type;

    @ElementCollection
    private List<String> choices;
    private String answer;
}
