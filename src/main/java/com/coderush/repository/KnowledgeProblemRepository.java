package com.coderush.repository;

import com.coderush.domain.entity.KnowledgeProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface KnowledgeProblemRepository extends JpaRepository<KnowledgeProblem, Long> {
}
