package com.maxswaine.gig.controller;

import com.maxswaine.gig.api.requests.AuthenticationRequest;
import com.maxswaine.gig.api.responses.AuthenticationResponse;
import com.maxswaine.gig.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest loginRequest
    ){
        return new ResponseEntity<>(authenticationService.authenticateLogin(loginRequest), HttpStatus.OK);
    }
}
