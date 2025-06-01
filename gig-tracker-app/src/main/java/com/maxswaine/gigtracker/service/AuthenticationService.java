package com.maxswaine.gigtracker.service;

import com.maxswaine.gigtracker.api.dto.User;
import com.maxswaine.gigtracker.api.requests.AuthenticationRequest;
import com.maxswaine.gigtracker.api.responses.AuthenticationResponse;
import com.maxswaine.gigtracker.configuration.JWTUtils;
import com.maxswaine.gigtracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JWTUtils jwtUtils;

    public AuthenticationResponse authenticateLogin(AuthenticationRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            User user = userRepository.findByUsernameOrEmail(loginRequest.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username " + loginRequest.getUsername()));

            String jwtToken = jwtUtils.generateToken(user);

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (BadCredentialsException e) {
            throw new UsernameNotFoundException("Invalid username or password", e);
        }
    }
}
