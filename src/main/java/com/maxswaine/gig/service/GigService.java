package com.maxswaine.gig.service;

import com.maxswaine.gig.api.dto.Gig;
import com.maxswaine.gig.api.dto.Moment;
import com.maxswaine.gig.exception.GigNotFoundException;
import com.maxswaine.gig.repository.GigRepository;
import com.maxswaine.gig.repository.MomentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.System.err;

@Service
@Slf4j
public class GigService {

    private static final Logger logger = LoggerFactory.getLogger(GigService.class);
    private final GigRepository gigRepository;
    private final MomentRepository momentRepository;


    @Autowired
    public GigService(GigRepository gigRepository, MomentRepository momentRepository) {
        this.gigRepository = gigRepository;
        this.momentRepository = momentRepository;
    }

    public List<Gig> getAllGigs() {
        logger.info("Fetching all gigs");
        List<Gig> allGigs = gigRepository.findAll();
        logger.info("Retrieved {} gigs", allGigs.size()); // Logging the number of gigs retrieved
        return allGigs;
    }

    public Gig getGigById(String id) {
        logger.info("Fetching gig with id {}", id);
        return gigRepository.findById(id)
                .map(gig -> {
                    logger.info("Found gig with id {}: {}", id, gig);
                    return gig;
                })
                .orElseThrow(() -> new GigNotFoundException(id));
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
            logger.error("There are no gigs for artist {}", artist);
        }
        logger.info("Getting {} gigs:", gigsByArtist.size());
        return gigsByArtist;
    }

    public Gig addGigWithMoments(Gig gig) {
        logger.info("Gig created with moments:");
        logger.info(gig.toString());
        for (Moment moment : gig.getMoments()) {
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
            throw new GigNotFoundException(id);
        }
    }

    public Gig updateGigPartial(String id, Map<String, Object> updates) {
        Gig gigToUpdate = gigRepository.findById(id).orElseThrow();

        updates.forEach((key, value) -> {
            switch (key) {
                case "artist":
                    gigToUpdate.setArtist((String) value);
                    break;
                case "venue":
                    gigToUpdate.setVenue((String) value);
                    break;
                case "location":
                    gigToUpdate.setLocation((String) value);
                    break;
                case "date":
                    gigToUpdate.setDate((LocalDateTime) value);
                    break;
                case "favourite":
                    gigToUpdate.setFavourite((Boolean) value);
                    break;
                case "moments":
                    List<Map<String, Object>> moments = (List<Map<String, Object>>) value;
                    for (Map<String, Object> momentData : moments) {
                        String momentId = (String) momentData.get("id");
                        String description = (String) momentData.get("description");
                        Optional<Moment> momentOptional = momentRepository.findById(momentId);
                        if (momentOptional.isPresent()) {
                            Moment moment = momentOptional.get();
                            moment.setDescription(description);
                            momentRepository.save(moment);
                        } else {
                            Moment newMoment = new Moment(description);
                            newMoment.setGig(gigToUpdate);
                            gigToUpdate.getMoments().add(newMoment);
                            momentRepository.save(newMoment);
                        }
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });

        logger.info("Gig Updated!");

        return gigRepository.save(gigToUpdate);
    }
}
