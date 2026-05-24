package com.smartcollab.user;

import com.smartcollab.security.JwtService;
import com.smartcollab.user.dto.LoginRequest;
import com.smartcollab.user.dto.LoginResponse;
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
    private final JwtService jwtService;

    public UserController(
            UserService userService,
            JwtService jwtService
    ) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {
        User user = userService.register(request);
        return new UserResponse(user);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        User user = userService.login(request);

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponse(token);
    }

    @GetMapping("/validate")
    public String validateToken(
            @RequestHeader("Authorization") String authHeader
    ) {

        String token = authHeader.replace("Bearer ", "");

        boolean valid = jwtService.isTokenValid(token);

        if (!valid) {
            return "Invalid token";
        }

        return jwtService.extractEmail(token);
    }
}