package com.maxswaine.gig.controller;

import com.maxswaine.gig.api.dto.Gig;
import com.maxswaine.gig.service.GigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gig")
@Slf4j
public class GigController {

    private final GigService gigService;

    @Autowired
    public GigController(GigService gigService) {
        this.gigService = gigService;
    }

    // READ
    @GetMapping
    public List<Gig> getAllGigs() {
        gigService.getAllGigs();
        return gigService.getAllGigs().getBody();

    }

    @GetMapping(params = "artist")
    public List<Gig> getGigByArtist(String artist) {
        if (artist != null) {
            artist = artist.replace("_", " ");
        }
        return gigService.getGigsByArtist(artist);
    }

    @GetMapping(params = "id")
    public Gig getGigById(Long id){
        return gigService.getGigbyId(id);
    }

    // CREATE
    @PostMapping
    public void addGig(@RequestBody Gig gig) {
        gigService.addGig(gig);
    }

    // DELETE
    @DeleteMapping(path = "{gigId}")
    public void deleteGig(@PathVariable("gigId") Long id) {
        gigService.deleteGig(id);
    }

    // UPDATE
    @PutMapping(path = "{gigId}")
    public void updateGig(@PathVariable("gigId") Long id, @RequestBody Gig gig) {
        gigService.updateGig(id, gig.getArtist(), gig.getVenue(), gig.getLocation(), gig.getDate(), gig.isFavourite());
    }
}
