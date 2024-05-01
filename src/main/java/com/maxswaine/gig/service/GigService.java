package com.maxswaine.gig.service;

import com.maxswaine.gig.api.dto.Gig;
import com.maxswaine.gig.repository.GigRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class GigService {

    private final GigRepository gigRepository;

    private static final Logger logger = LoggerFactory.getLogger(GigService.class);


    @Autowired
    public GigService(GigRepository gigRepository) {
        this.gigRepository = gigRepository;
    }

    public ResponseEntity<List<Gig>> getAllGigs() {
        log.info("Received request to get all gigs.");
        return new ResponseEntity<>(gigRepository.findAll(), HttpStatus.OK);
    }

    public Gig getGigbyId(Long id) {
        Gig gigById = gigRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Gig not found with that id " + id));
        System.out.println(gigById);
        return gigById;
    }

    public List<Gig> getGigsByArtist(String artist) {
        List<Gig> gigsByArtist = gigRepository.findGigsByArtistContainingIgnoreCase(artist);
        if(gigsByArtist.isEmpty()){
            logger.error("There are no artists that contain {}", artist);
        }
        System.out.println("Getting " + gigsByArtist.size() + " artists:\n");
        for (Gig gig : gigsByArtist) {
            System.out.println(gig.getArtist() + "\n");
        }
        return gigsByArtist;
    }

    public void addGig(Gig gig) {
        gigRepository.save(gig);
    }

    public void deleteGig(Long id) {
        boolean exists = gigRepository.existsById(id);
        if (!exists) {
            logger.error("Gig does not exist with id: {}", id);
        }
        gigRepository.deleteById(id);
    }

    @Transactional
    public void updateGig(Long id, String artist, String venue, String location, LocalDateTime date, boolean favourite) {
        Gig gig = gigRepository.getReferenceById(id);
        if (artist != null && !artist.isEmpty() && !gig.getArtist().equals(artist)) {
            gig.setArtist(artist);
        }
        if (venue != null && !venue.isEmpty() && !gig.getVenue().equals(venue)) {
            gig.setVenue(venue);
        }
        if (location != null && !location.isEmpty() && !gig.getLocation().equals(location)) {
            gig.setLocation(location);
        }
        if (date != null && !gig.getDate().equals(date)) {
            gig.setDate(date);
        }
        if (favourite != gig.isFavourite()) {
            gig.setFavourite(favourite);
        }

        System.out.println("Updated Gig:\n" + gig);

    }

}
