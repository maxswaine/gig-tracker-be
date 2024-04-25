package com.maxswaine.gig.service;

import com.maxswaine.gig.api.dto.Gig;
import com.maxswaine.gig.api.dto.Moment;
import com.maxswaine.gig.repository.GigRepository;
import com.maxswaine.gig.repository.MomentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MomentService {

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
    public void addMoment(Moment moment) {
        momentRepository.save(moment);
    }

    // DELETE
    public void deleteMoment(Long id) {
        boolean exists = momentRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Moment does not exist");
        }
        momentRepository.deleteById(id);
    }

    //     UPDATE
    @Transactional
    public void updateMoment(Long id, String moment) {
    }
}
