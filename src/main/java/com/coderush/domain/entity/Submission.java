package com.coderush.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "submissions")
@Data
@NoArgsConstructor
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long problemId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String submittedAnswer;
    private Boolean correct;
}
