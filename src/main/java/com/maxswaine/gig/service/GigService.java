package com.maxswaine.gig.service;

import com.maxswaine.gig.api.dto.Gig;
import com.maxswaine.gig.api.dto.Moment;
import com.maxswaine.gig.api.dto.User;
import com.maxswaine.gig.api.requests.GigRequest;
import com.maxswaine.gig.repository.GigRepository;
import com.maxswaine.gig.repository.MomentRepository;
import com.maxswaine.gig.repository.UserRepository;
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

@Service
@Slf4j
public class GigService {

    private static final Logger logger = LoggerFactory.getLogger(GigService.class);
    private final GigRepository gigRepository;
    private final MomentRepository momentRepository;
    private final UserRepository userRepository;


    @Autowired
    public GigService(GigRepository gigRepository, MomentRepository momentRepository, UserRepository userRepository) {
        this.gigRepository = gigRepository;
        this.momentRepository = momentRepository;
        this.userRepository = userRepository;
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
                .orElseThrow(() -> {
                    logger.error("Gig not found with id {}", id);
                    return new EntityNotFoundException(id);
                });
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

    public Gig addGigWithMoments(GigRequest gigRequest) {
        User user = userRepository.findById(gigRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Moment> moments = gigRequest.getMoments();

        Gig gig = Gig.builder()
                .artist(gigRequest.getArtist())
                .venue(gigRequest.getVenue())
                .location(gigRequest.getLocation())
                .date(gigRequest.getDate())
                .favourite(gigRequest.isFavourite())
                .user(user)
                .moments(moments)
                .build();

        // Set the gig for each moment if moments are present
        if (moments != null) {
            for (Moment moment : moments) {
                moment.setGig(gig);
            }
        }

        Gig savedGig = gigRepository.save(gig);
        logger.info("Gig created with moments: {}", savedGig);
        return savedGig;

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
