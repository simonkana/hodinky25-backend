package cz.hodinky25.backend.user.payload;

public record UserResponse(
        Long id,
        String username,
        String email,
        String role
) {
}
