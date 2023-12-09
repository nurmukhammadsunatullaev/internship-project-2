package com.project.persistence.repository;

import com.project.persistence.entity.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Credentials, Long> {
    Optional<Credentials> findByLogin(String login);
    boolean existsByLogin(String login);
}
