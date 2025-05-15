package com.coderush.repository;

import com.coderush.domain.entity.Submission;
import com.coderush.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByUser(User user);

    List<Submission> findByUser_Id(Long userId);
}
