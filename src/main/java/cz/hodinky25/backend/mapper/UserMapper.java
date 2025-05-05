package cz.hodinky25.backend.mapper;

import cz.hodinky25.backend.model.User;
import cz.hodinky25.backend.payload.response.UserResponse;

public class UserMapper {

    public static UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}
