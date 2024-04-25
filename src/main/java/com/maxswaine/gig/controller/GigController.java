package com.maxswaine.gig.controller;

import com.maxswaine.gig.api.dto.Gig;
import com.maxswaine.gig.service.GigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gig")
public class GigController {

    private final GigService gigService;

    @Autowired
    public GigController(GigService gigService) {
        this.gigService = gigService;
    }

    // READ
    @GetMapping
    public List<Gig> getAllGigs() {
        return gigService.getAllGigs();
    }

    @GetMapping(params = "artist")
    public List<Gig> getGigByArtist(@RequestParam(required = false) String artist) {
        if (artist != null) {
            artist = artist.replace("_", " ");
        }
        return gigService.getGigsByArtist(artist);
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
