package com.maxswaine.gig.service;

import com.maxswaine.gig.api.dto.User;
import com.maxswaine.gig.api.requests.AuthenticationRequest;
import com.maxswaine.gig.api.responses.AuthenticationResponse;
import com.maxswaine.gig.configuration.JWTUtils;
import com.maxswaine.gig.repository.UserRepository;
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
                    new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
            );

            User user = userRepository.findByUsername(loginRequest.username())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username " + loginRequest.username()));

            String jwtToken = jwtUtils.generateToken(user);

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (BadCredentialsException e) {
            throw new UsernameNotFoundException("Invalid username or password", e);
        }
    }
}
