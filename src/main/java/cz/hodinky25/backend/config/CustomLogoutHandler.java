package cz.hodinky25.backend.config;

import cz.hodinky25.backend.model.Token;
import cz.hodinky25.backend.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String token = authHeader.substring(7);

        // get the stored token from the database
        Token storedToken = tokenRepository.findByToken(token).orElse(null);

        // invalidate the token (set it to inactive)
        if (storedToken != null) {
            storedToken.setActive(false);
            tokenRepository.save(storedToken);
        }
    }
}
