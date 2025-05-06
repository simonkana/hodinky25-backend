package cz.hodinky25.backend.service;

import cz.hodinky25.backend.exception.ResourceAlreadyExistsException;
import cz.hodinky25.backend.model.Role;
import cz.hodinky25.backend.model.Token;
import cz.hodinky25.backend.model.User;
import cz.hodinky25.backend.payload.request.LoginRequest;
import cz.hodinky25.backend.payload.request.RegistrationRequest;
import cz.hodinky25.backend.payload.response.AuthResponse;
import cz.hodinky25.backend.repository.TokenRepository;
import cz.hodinky25.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public String register(RegistrationRequest request) {

        if (userRepository.existsByUsername(request.username())) {
            throw new ResourceAlreadyExistsException("User with this username already exists");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new ResourceAlreadyExistsException("User with this email already exists");
        }

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return "User was successfully registered. Please log in.";
    }

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        User user = userRepository.findByUsername(request.username()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String authToken = jwtService.generateToken(user);

        // invalidate all previously generated tokens associated with this user
        List<Token> validTokens = tokenRepository.findAllByUserIdAndActiveTrue(user.getId());
        if (!validTokens.isEmpty()) {
            validTokens.forEach(token -> token.setActive(false));
            tokenRepository.saveAll(validTokens);
        }

        // save the generated token to the database
        Token token = Token.builder()
                .token(authToken)
                .active(true)
                .user(user)
                .build();
        tokenRepository.save(token);

        return new AuthResponse(authToken);
    }
}
