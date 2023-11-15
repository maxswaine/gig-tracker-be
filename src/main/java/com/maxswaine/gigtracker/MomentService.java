package com.maxswaine.gigtracker;

import com.maxswaine.gigtracker.models.Gig;
import com.maxswaine.gigtracker.models.Moment;
import com.maxswaine.gigtracker.repositories.GigRepository;
import com.maxswaine.gigtracker.repositories.MomentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MomentService {
    @Autowired
    GigRepository gigRepository;

    @Autowired
    MomentRepository momentRepository;

    // CREATE
    public Moment addMoment(Moment moment) {
        Moment newMoment = momentRepository.save(moment);
        System.out.println(newMoment);
        return newMoment;
    }


// READ

// UPDATE

// DELETE
}