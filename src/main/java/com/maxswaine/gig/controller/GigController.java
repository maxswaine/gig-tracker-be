package com.maxswaine.gig.controller;

import com.maxswaine.gig.api.dto.Gig;
import com.maxswaine.gig.repository.GigRepository;
import com.maxswaine.gig.service.GigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/gig")
public class GigController {

    private final GigService gigService;
    private final GigRepository gigRepository;

    @Autowired
    public GigController(GigService gigService, GigRepository gigRepository) {
        this.gigService = gigService;
        this.gigRepository = gigRepository;
    }

    @GetMapping
    public List<Gig> getGigs() {
        return gigRepository.findAll();
    }

    @PostMapping
    public void addGig(Gig gig) {
        gigService.addGig(gig);
    }

    @DeleteMapping(path = "{gigId}")
    public void deleteGig(@PathVariable("gigId") Long id) {
        gigService.deleteGig(id);
    }

    @PutMapping(path = "{gigId}")
    public void updateGig(@PathVariable("gigId") Long id, @RequestParam(required = false) String artist, @RequestParam(required = false) String venue, @RequestParam(required = false) String location, @RequestParam(required = false) LocalDate date, @RequestParam(required = false) boolean favourite) {
        gigService.updateGig(id, artist, venue, location, date, favourite);
    }
}
