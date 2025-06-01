package com.maxswaine.gigtracker.service;

import com.maxswaine.gigtracker.api.dto.Gig;
import com.maxswaine.gigtracker.api.dto.Moment;
import com.maxswaine.gigtracker.api.dto.User;
import com.maxswaine.gigtracker.api.requests.MomentRequest;
import com.maxswaine.gigtracker.repository.GigRepository;
import com.maxswaine.gigtracker.repository.MomentRepository;
import com.maxswaine.gigtracker.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MomentService {

    private static final Logger logger = LoggerFactory.getLogger(MomentService.class);

    private final MomentRepository momentRepository;
    private final GigRepository gigRepository;
    private final UserRepository userRepository;

    @Autowired
    public MomentService(MomentRepository momentRepository, GigRepository gigRepository, UserRepository userRepository) {
        this.momentRepository = momentRepository;
        this.gigRepository = gigRepository;
        this.userRepository = userRepository;
    }

    // READ
    public List<Moment> getMoments() {
        return momentRepository.findAll();
    }

    public List<Moment> getMomentsFromFavouriteGigs() {
        List<Gig> favouriteGigs = gigRepository.findGigByFavourite(true);
        List<Moment> favouriteMoments = new ArrayList<>();
        for (Gig gig : favouriteGigs) {
            List<Moment> moments = gig.getMoments();
            favouriteMoments.addAll(moments);
        }
        return favouriteMoments;
    }

    // CREATE
    public List<Moment> addMoments(MomentRequest momentRequest) {
        Gig gig = gigRepository.findById(momentRequest.getGigId())
                .orElseThrow(() -> new RuntimeException("Gig not found"));
        User user = userRepository.findById(momentRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(!gig.getAttendees().contains(user)){
            throw new RuntimeException("User is not part of the gig");
        }
        List<Moment> moments = new ArrayList<>();

        for(String description: momentRequest.getDescriptions()){
            Moment moment = Moment.builder()
                    .user(user)
                    .gig(gig)
                    .description(description)
                    .build();
            moments.add(moment);
            logger.info("Added moment {}", description);
        }
        return momentRepository.saveAll(moments);
    }

    // DELETE
    public void deleteMoment(String id) {
        boolean exists = momentRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Moment does not exist");
        }
        momentRepository.deleteById(id);
    }
}
