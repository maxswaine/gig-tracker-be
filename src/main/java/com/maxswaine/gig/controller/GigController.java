package com.maxswaine.gig.controller;

import com.maxswaine.gig.api.dto.Gig;
import com.maxswaine.gig.service.GigService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    // CREATE
    @PostMapping
    public ResponseEntity<Gig> addGigWithMoments(@RequestBody Gig gig) {
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
    public ResponseEntity<Gig> updateGig(@PathVariable String id, @RequestBody Gig gigUpdate) {
        try {
            Gig existingGig = gigService.getGigById(id);
            if(gigUpdate.getMoments().isEmpty()){
                BeanUtils.copyProperties(gigUpdate, existingGig, "id", "moments");
                return new ResponseEntity<>(gigService.addGig(gig), HttpStatus.OK);
            } else {
                BeanUtils.copyProperties(gigUpdate, existingGig, "id", "moments");
                return new ResponseEntity<>(gigService.addGigWithMoments(gigUpdate), HttpStatus.OK);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
