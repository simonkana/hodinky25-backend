package cz.hodinky25.backend.user.mapper;

import cz.hodinky25.backend.user.model.User;
import cz.hodinky25.backend.user.payload.UserResponse;

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
