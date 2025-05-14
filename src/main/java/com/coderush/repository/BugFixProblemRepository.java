package com.coderush.repository;

import com.coderush.domain.entity.BugFixProblem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BugFixProblemRepository extends JpaRepository<BugFixProblem, Long> {
}
