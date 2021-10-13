package com.jinho.homepage.repository;

import com.jinho.homepage.entity.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailTokenRepository extends JpaRepository<EmailToken, String> {
    Optional<EmailToken> findByIdAndExpirationDateAfterAndExpired(
            String confirmationTokenId,
            LocalDateTime now,
            boolean expired
    );
}
