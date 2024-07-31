package com.maxswaine.gig.controller;

import com.maxswaine.gig.api.requests.UserRequest;
import com.maxswaine.gig.api.responses.AuthenticationResponse;
import com.maxswaine.gig.repository.UserRepository;
import com.maxswaine.gig.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    // READ

    // CREATE
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest request) {
        System.out.println("UserRequest received: " + request);
        if (request == null) {
            return ResponseEntity.badRequest().body("Request body is null");
        }
        // Process the request
        AuthenticationResponse response = userService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    // DELETE
    @DeleteMapping(path = "{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") String id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // UPDATE
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateUserPassword(@PathVariable String id, @RequestBody String password) {
        if (userRepository.existsById(id)) {
            userService.updatePassword(id, password);
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.badRequest().build();
        }
    }
}
