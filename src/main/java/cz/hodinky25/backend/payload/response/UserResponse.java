package cz.hodinky25.backend.payload.response;

public record UserResponse(
        Long id,
        String username,
        String email,
        String role
) {
}
