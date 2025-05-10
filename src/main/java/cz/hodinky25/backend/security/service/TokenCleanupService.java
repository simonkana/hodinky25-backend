package cz.hodinky25.backend.security.service;

import cz.hodinky25.backend.security.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class TokenCleanupService {

    private final TokenRepository tokenRepository;

    @Scheduled(cron = "0 0 3 * * ?") // každý den ve 3:00 ráno
    public void cleanupOldInactiveTokens() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        tokenRepository.deleteInactiveTokensOlderThan(sevenDaysAgo);
    }
}
