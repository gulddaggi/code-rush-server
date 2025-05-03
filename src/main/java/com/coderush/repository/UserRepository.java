package com.coderush.repository;

import com.coderush.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User FindByNickname(String nickname);
}
