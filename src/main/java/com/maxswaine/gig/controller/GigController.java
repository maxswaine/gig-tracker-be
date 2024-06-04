package com.maxswaine.gig.controller;

import com.maxswaine.gig.api.dto.Gig;
import com.maxswaine.gig.api.requests.GigRequest;
import com.maxswaine.gig.service.GigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/gigs")
public class GigController {

    private final GigService gigService;

    @Autowired
    public GigController(GigService gigService) {
        this.gigService = gigService;
    }

    // READ
    @GetMapping
    public ResponseEntity<List<Gig>> getAllGigs() {
        List<Gig> allGigs = gigService.getAllGigs();
        return new ResponseEntity<>(allGigs, HttpStatus.OK);
    }

    @GetMapping(params = "artist")
    public ResponseEntity<List<Gig>> getGByArtist(String artist) {
        if (artist != null) {
            artist = artist.replace("_", " ");
        }
        return new ResponseEntity<>(gigService.getGigsByArtist(artist), HttpStatus.OK);
    }

    @GetMapping(params = "venue")
    public ResponseEntity<List<Gig>> getGigsByVenue(String venue) {
        return new ResponseEntity<>(gigService.getGigsbyVenue(venue), HttpStatus.OK);
    }

    @GetMapping(path = "{gigId}")
    public ResponseEntity<Gig> getGigById(@PathVariable("gigId") String id){
        return new ResponseEntity<>(gigService.getGigById(id), HttpStatus.OK);
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Gig> addGigWithMoments(@RequestBody GigRequest gig) {
        Gig savedGig = gigService.addGigWithMoments(gig);
        return new ResponseEntity<>(savedGig, HttpStatus.CREATED);
    }

    // DELETE
    @DeleteMapping(path = "{gigId}")
    public ResponseEntity<Void> deleteGig(@PathVariable("gigId") String id) {
        try {
            gigService.deleteGig(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // UPDATE
    @PatchMapping("/{id}")
    public ResponseEntity<Gig> updateGigPartial(@PathVariable String id, @RequestBody Map<String, Object> updates) {
        try {
            Gig updatedGig = gigService.updateGigPartial(id, updates);
            return ResponseEntity.ok(updatedGig);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

