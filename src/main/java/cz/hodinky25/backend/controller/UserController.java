package cz.hodinky25.backend.controller;

import cz.hodinky25.backend.mapper.UserMapper;
import cz.hodinky25.backend.model.User;
import cz.hodinky25.backend.payload.response.UserResponse;
import cz.hodinky25.backend.security.CurrentUser;
import cz.hodinky25.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@CurrentUser User user) {
        return ResponseEntity.ok(UserMapper.toUserResponse(user));
    }
}
