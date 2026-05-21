package com.smartcollab.user;

import com.smartcollab.user.dto.LoginRequest;
import com.smartcollab.user.dto.RegisterRequest;
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
    public User register(@Valid @RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public User login(@Valid @RequestBody LoginRequest request) {
        return userService.login(request);
    }
}