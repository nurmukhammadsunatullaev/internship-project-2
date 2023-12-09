package com.project.persistence.repository;

import com.project.persistence.entity.Credentials;
import com.project.persistence.entity.RefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByCredentials_Id(long credentialsId);
    boolean existsByCredentials_Id(long credentialsId);

    @Modifying
    @Transactional
    @Query("update RefreshToken u set u.token = :token where u.credentials = :credentials")
    void changeToken(@Param("credentials") Credentials credentials, String token);
}
