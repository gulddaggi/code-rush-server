package com.coderush.domain.entity;

import com.coderush.domain.enums.ProblemType;
import com.coderush.domain.problem.ProblemCategory;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "submissions")
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long problemId;

    @Enumerated(EnumType.STRING)
    private ProblemType problemType;

    @Enumerated(EnumType.STRING)
    private ProblemCategory category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String submittedAnswer;
    private Boolean correct;
}
