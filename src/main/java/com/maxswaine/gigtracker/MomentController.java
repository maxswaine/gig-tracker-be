package com.maxswaine.gigtracker;

import com.maxswaine.gigtracker.models.Gig;
import com.maxswaine.gigtracker.models.Moment;
import com.maxswaine.gigtracker.repositories.GigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class MomentController {

    @Autowired
    private MomentService momentService;
    @Autowired
    private GigService gigService;
    @Autowired
    private GigRepository gigRepository;

    @ExceptionHandler
    public ResponseEntity<String> handleExceptions(Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    // CREATE
    @PostMapping("/moment/{gigId}")
    public ResponseEntity<Moment> createMomentForGig(@PathVariable Long gigId, @RequestBody Moment moment) {

        Optional<Gig> optionalGig = gigRepository.findById(gigId);
        if (optionalGig.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        moment.setGig(optionalGig.get());
        Moment newMoment = momentService.addMoment(moment);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMoment);
    }

    // READ

    // UPDATE

    // DELETE
}
