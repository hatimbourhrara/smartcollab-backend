package com.smartcollab.user.service;

import com.smartcollab.user.dto.RegisterRequest;
import com.smartcollab.user.model.User;
import com.smartcollab.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        return userRepository.save(user);
    }
}