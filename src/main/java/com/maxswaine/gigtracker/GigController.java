package com.maxswaine.gigtracker;

import com.maxswaine.gigtracker.models.Gig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class GigController {

    @Autowired
    private GigService gigService;

    @ExceptionHandler
    public ResponseEntity<String> handleExceptions(Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    // CREATE
    @PostMapping("/gig")
    public ResponseEntity<Gig> createGig(@RequestBody Gig gig) {
        Gig newGig = gigService.addGig(gig);
        return ResponseEntity.status(HttpStatus.CREATED).body(newGig);
    }

    // READ
    @GetMapping("/gigs")
    public ResponseEntity<List<Gig>> getGigs(@RequestParam(required = false) String artist, @RequestParam(defaultValue = "15") int limit) {
        if (artist != null) {
            return ResponseEntity.status(HttpStatus.OK).body(gigService.getGigsByArtist(artist, limit));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gigService.getAllGigs(limit));
    }

    @GetMapping("/gigs/")
    public ResponseEntity<Gig> getGigById(@RequestParam(required = false) Long id){
        return ResponseEntity.status(HttpStatus.OK).body(gigService.getGigsById(id));
    }

    // UPDATE

    // DELETE
}
