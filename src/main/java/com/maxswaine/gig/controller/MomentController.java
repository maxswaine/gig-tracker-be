package com.maxswaine.gig.controller;

import com.maxswaine.gig.api.dto.Moment;
import com.maxswaine.gig.service.MomentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/moment")
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
    public void addMoment(Moment moment) {
        momentService.addMoment(moment);
    }

    @DeleteMapping(path = "{momentId}")
    public void deleteMoment(@PathVariable("momentId") Long id) {
        momentService.deleteMoment(id);
    }

    @PutMapping(path = "{momentId}")
    public void updateMoment(@PathVariable("momentId") Long id, @RequestParam(required = false) String moment) {
        momentService.updateMoment(id, moment);
    }
}
