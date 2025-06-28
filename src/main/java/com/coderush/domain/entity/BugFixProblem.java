package com.coderush.domain.entity;

import com.coderush.domain.problem.ProblemCategory;
import com.coderush.domain.problem.ProblemType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bug_fix_problems")
public class BugFixProblem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private ProblemType type;

    @Enumerated(EnumType.STRING)
    private ProblemCategory category;

    @ElementCollection
    private List<String> choices;
    private String answer;

    @Column(columnDefinition = "TEXT")
    private String targetSnippet;
    @Column(columnDefinition = "TEXT")
    private String correctFix;
}

