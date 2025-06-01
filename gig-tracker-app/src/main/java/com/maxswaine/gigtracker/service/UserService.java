package com.maxswaine.gigtracker.service;

import com.maxswaine.gigtracker.api.dto.User;
import com.maxswaine.gigtracker.api.enums.Role;
import com.maxswaine.gigtracker.api.requests.UserRequest;
import com.maxswaine.gigtracker.api.responses.AuthenticationResponse;
import com.maxswaine.gigtracker.configuration.JWTUtils;
import com.maxswaine.gigtracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;

    public AuthenticationResponse registerUser(UserRequest request) {
        User newUser = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        logger.info("Creating new user");
        userRepository.save(newUser);

        String jwtToken = jwtUtils.generateToken(newUser);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void deleteUser(String id) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            logger.info("User {} successfully deleted", existingUser.get().getId());
            userRepository.deleteById(id);
        } else {
            logger.error("User with id {} does not exist", id);
            throw new IllegalArgumentException();
        }
    }

    public void updatePassword(String id, String password) {
        User existingUser = userRepository.findById(id).orElseThrow();

        existingUser.setPassword(password);
        logger.info("Password updated successfully");
    }
}
