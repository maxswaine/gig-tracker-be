package com.maxswaine.gig.service;

import com.maxswaine.gig.api.dto.Gig;
import com.maxswaine.gig.api.dto.Moment;
import com.maxswaine.gig.repository.GigRepository;
import com.maxswaine.gig.repository.MomentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MomentService {

    private static final Logger logger = LoggerFactory.getLogger(GigService.class);

    private final MomentRepository momentRepository;
    private final GigRepository gigRepository;

    @Autowired
    public MomentService(MomentRepository momentRepository, GigRepository gigRepository) {
        this.momentRepository = momentRepository;
        this.gigRepository = gigRepository;
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
    public void addMoments(List<Moment> moments) {
        logger.info("Saving {} moments associated with gig", moments.size());
        momentRepository.saveAll(moments);
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
