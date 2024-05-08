package com.maxswaine.gig.service;

import com.maxswaine.gig.api.dto.Gig;
import com.maxswaine.gig.repository.GigRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public Gig addGig(Gig gig) {
        logger.info("Gig created:");
        logger.info(gig.toString());
        return gigRepository.save(gig);
    }

    public void deleteGig(Long id) {
        boolean exists = gigRepository.existsById(id);
        if (!exists) {
            logger.error("Gig does not exist with id: {}", id);
        } else {
            logger.info("Gig {} successfully deleted", id);
        }
        gigRepository.deleteById(id);
    }

    @Transactional
    public Gig updateGig(Long id, String artist, String venue, String location, LocalDateTime date, boolean favourite) {
        Gig gig = gigRepository.getReferenceById(id);
        int changes = 0;

        if (artist != null && !artist.isEmpty() && !gig.getArtist().equals(artist)) {
            changes++;
            logger.info("Artist changed to {}", artist);
            gig.setArtist(artist);
        }
        if (venue != null && !venue.isEmpty() && !gig.getVenue().equals(venue)) {
            changes++;
            logger.info("Venue changed to {}", venue);
            gig.setVenue(venue);
        }
        if (location != null && !location.isEmpty() && !gig.getLocation().equals(location)) {
            changes++;
            logger.info("Location changed to {}", location);
            gig.setLocation(location);
        }
        if (date != null && !gig.getDate().equals(date)) {
            changes++;
            logger.info("Date changed to {}", date);
            gig.setDate(date);
        }
        if (favourite != gig.isFavourite()) {
            changes++;
            if (favourite){
                logger.info("This gig is now favourited");
            } else{
                logger.info("This gig has been unfavourited");
            }
            gig.setFavourite(favourite);
        }

        logger.info("A total of {} changes were made to Gig with id {}", changes, id);

        return gig;

    }

}
