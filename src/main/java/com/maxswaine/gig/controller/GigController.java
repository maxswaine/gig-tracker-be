package com.maxswaine.gig.controller;

import com.maxswaine.gig.api.dto.Gig;
import com.maxswaine.gig.api.dto.Moment;
import com.maxswaine.gig.service.GigService;
import com.maxswaine.gig.service.MomentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gig")
public class GigController {

    private final GigService gigService;
    private final MomentService momentService;

    @Autowired
    public GigController(GigService gigService, MomentService momentService) {
        this.gigService = gigService;
        this.momentService = momentService;
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
    public ResponseEntity<List<Gig>> getGigsByVenue(String venue){
        return new ResponseEntity<>(gigService.getGigsbyVenue(venue), HttpStatus.OK);
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Void> addGigWithMoments(@RequestBody Gig gig) {
        Gig savedGig = gigService.addGig(gig);

        if (savedGig != null && savedGig.getId() != null && !savedGig.getMoments().isEmpty()) {
            for (Moment moment : savedGig.getMoments()) {
                moment.setGig(savedGig);
            }
            momentService.addMoments(savedGig.getMoments());
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }



    // DELETE
    @DeleteMapping(path = "{gigId}")
    public ResponseEntity<Void> deleteGig(@PathVariable("gigId") Long id) {
        gigService.deleteGig(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    // UPDATE
    @PutMapping(path = "{gigId}")
    public ResponseEntity<Gig> updateGig(@PathVariable("gigId") Long id, @RequestBody Gig gig) {
        Gig updatedGig = gigService.updateGig(id, gig.getArtist(), gig.getVenue(), gig.getLocation(), gig.getDate(), gig.isFavourite());
        return new ResponseEntity<>(updatedGig, HttpStatus.OK);
    }
}
