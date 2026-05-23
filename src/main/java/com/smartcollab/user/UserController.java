package com.smartcollab.user;

import com.smartcollab.user.dto.LoginRequest;
import com.smartcollab.user.dto.RegisterRequest;
import com.smartcollab.user.dto.UserResponse;
import com.smartcollab.user.model.User;
import com.smartcollab.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {
        User user = userService.register(request);
        return new UserResponse(user);
    }

    @PostMapping("/login")
    public UserResponse login(@Valid @RequestBody LoginRequest request) {
        User user = userService.login(request);
        return new UserResponse(user);
    }
}