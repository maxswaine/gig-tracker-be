package com.maxswaine.gigtracker.controller;


import com.maxswaine.gigtracker.api.dto.Moment;
import com.maxswaine.gigtracker.api.requests.MomentRequest;
import com.maxswaine.gigtracker.service.MomentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/moments")
public class MomentController {

    private final MomentService momentService;

    @Autowired
    public MomentController(MomentService momentService) {
        this.momentService = momentService;
    }

    @GetMapping
    public List<Moment> getMoments() {
        return momentService.getMoments();
    }

    @GetMapping(params = "favourite")
    public List<Moment> getFavouriteMoments() {
        return momentService.getMomentsFromFavouriteGigs();
    }

    @PostMapping
    public ResponseEntity<List<Moment>> addMoments(@RequestBody MomentRequest moments) {
        List<Moment> savedMoments = momentService.addMoments(moments);
        return new ResponseEntity<>(savedMoments, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{momentId}")
    public void deleteMoment(@PathVariable("momentId") String id) {
        momentService.deleteMoment(id);
    }
}
