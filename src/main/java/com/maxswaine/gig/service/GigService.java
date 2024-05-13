package com.maxswaine.gig.service;

import com.maxswaine.gig.api.dto.Gig;
import com.maxswaine.gig.api.dto.Moment;
import com.maxswaine.gig.repository.GigRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GigService {

    private static final Logger logger = LoggerFactory.getLogger(GigService.class);
    private final GigRepository gigRepository;


    @Autowired
    public GigService(GigRepository gigRepository) {
        this.gigRepository = gigRepository;
    }

    public List<Gig> getAllGigs() {
        logger.info("Fetching all gigs");
        List<Gig> allGigs = gigRepository.findAll();
        logger.info("Retrieved {} gigs", allGigs.size()); // Logging the number of gigs retrieved
        return allGigs;
    }

    public Gig getGigById(String id) {
        logger.info("Fetching gig with id {}", id);
        Optional<Gig> gigById = gigRepository.findById(id);
        return gigById.orElse(null);
    }

    public List<Gig> getGigsbyVenue(String venue) {
        List<Gig> gigsByVenue = gigRepository.findGigsByVenueContainingIgnoreCase(venue);
        if (gigsByVenue.isEmpty()) {
            logger.warn("No gigs found with that venue");
        }
        logger.info("Found {} gigs at that venue", gigsByVenue.size());
        return gigsByVenue;
    }

    public List<Gig> getGigsByArtist(String artist) {
        List<Gig> gigsByArtist = gigRepository.findGigsByArtistContainingIgnoreCase(artist);
        if (gigsByArtist.isEmpty()) {
            logger.error("There are no artists that contain {}", artist);
        }
        System.out.println("Getting " + gigsByArtist.size() + " artists:\n");
        for (Gig gig : gigsByArtist) {
            System.out.println(gig.getArtist() + "\n");
        }
        return gigsByArtist;
    }

    public Gig addGigWithMoments(Gig gig) {
        logger.info("Gig created with moments:");
        logger.info(gig.toString());
        for(Moment moment: gig.getMoments()){
            moment.setGig(gig);
        }
        return gigRepository.save(gig);
    }

    public void deleteGig(String id) {
        Optional<Gig> gig = gigRepository.findById(id);
        if (gig.isPresent()) {
            logger.info("Gig {} successfully deleted", id);
            gigRepository.deleteById(id);
        } else {
            logger.error("Gig does not exist with id: {}", id);
            throw new IllegalArgumentException("Gig with id " + id + " does not exist");
        }
    }

}
