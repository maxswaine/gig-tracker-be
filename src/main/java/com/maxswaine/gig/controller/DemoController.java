package com.maxswaine.gig.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
@RequiredArgsConstructor
public class DemoController {

    @PostMapping()
    public ResponseEntity<String> demo(){
        return new ResponseEntity<>("Hello from a secured endpoint", HttpStatus.OK);
    }
}