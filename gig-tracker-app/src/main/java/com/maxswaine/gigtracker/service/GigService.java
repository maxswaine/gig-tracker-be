package com.maxswaine.gigtracker.service;

import com.maxswaine.gigtracker.api.dto.Gig;
import com.maxswaine.gigtracker.api.dto.User;
import com.maxswaine.gigtracker.api.requests.GigRequest;
import com.maxswaine.gigtracker.repository.GigRepository;
import com.maxswaine.gigtracker.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class GigService {

    private static final Logger logger = LoggerFactory.getLogger(GigService.class);
    private final GigRepository gigRepository;
    private final UserRepository userRepository;
    private final FriendRequestService friendRequestService;


    @Autowired
    public GigService(GigRepository gigRepository, UserRepository userRepository, FriendRequestService friendRequestService) {
        this.gigRepository = gigRepository;
        this.userRepository = userRepository;
        this.friendRequestService = friendRequestService;
    }

    public List<Gig> getAllGigs() {
        logger.info("Fetching all gigs");
        List<Gig> allGigs = gigRepository.findAll();
        logger.info("Retrieved {} gigs", allGigs.size());
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
        logger.info("Getting {} artists:\n", gigsByArtist.size());
        for (Gig gig : gigsByArtist) {
            logger.info("{} + \n", gig.getArtist() );
        }
        return gigsByArtist;
    }

    public Gig addGigWithUsers(GigRequest gigRequest) {
        User user = userRepository.findById(gigRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<User> attendees = new ArrayList<>();
        attendees.add(user);
        for (String attendeeId : gigRequest.getAttendeeIds()) {
            if (!attendeeId.equals(gigRequest.getUserId())) {
                User attendee = userRepository.findById(attendeeId)
                        .orElseThrow(RuntimeException::new);
                if(friendRequestService.areFriends(gigRequest.getUserId(), attendeeId)){
                    attendees.add(attendee);
                } else {
                    logger.warn("User with ID {} is not friends with the user. Skipping this user", attendeeId);
                }
            }
        }

        Gig gig = Gig.builder()
                .artist(gigRequest.getArtist())
                .venue(gigRequest.getVenue())
                .location(gigRequest.getLocation())
                .date(gigRequest.getDate())
                .favourite(gigRequest.isFavourite())
                .attendees(attendees)
                .build();

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
                case "attendeeIds":
                    List<String> attendees = (List<String>) value;
                    List<User> users = gigToUpdate.getAttendees();
                    for(String userId: attendees){
                        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User with ID " + userId + " not found"));
                        if(!gigToUpdate.getAttendees().contains(user)){
                            users.add(user);
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
