package cz.hodinky25.backend.security.repository;

import cz.hodinky25.backend.security.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> findAllByUserIdAndActiveTrue(Long userId);

    Optional<Token> findByToken(String token);

    @Modifying
    @Query("DELETE FROM Token t WHERE t.active = false AND t.createdAt < :timestamp")
    void deleteInactiveTokensOlderThan(@Param("timestamp") LocalDateTime timestamp);
}