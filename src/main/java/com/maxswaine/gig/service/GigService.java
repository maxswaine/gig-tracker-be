package com.maxswaine.gig.service;

import com.maxswaine.gig.api.dto.Gig;
import com.maxswaine.gig.api.dto.Moment;
import com.maxswaine.gig.repository.GigRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class GigService {

    private final GigRepository gigRepository;

    @Autowired
    public GigService(GigRepository gigRepository) {
        this.gigRepository = gigRepository;
    }

    public List<Gig> gigs() {
        return gigRepository.findAll();
    }

    public void addGig(Gig gig){
        gigRepository.save(gig);
    }

    public void deleteGig(Long id) {
        boolean exists = gigRepository.existsById(id);
        if (!exists){
            throw new IllegalStateException("Gig does not exist with id: " + id + ".");
        }
        gigRepository.deleteById(id);
    }

    @Transactional
    public void updateGig(Long id, String artist, String venue, String location, LocalDate date, boolean favourite){
        Gig gig = gigRepository.getReferenceById(id);
        if (artist != null && artist.length() > 0 && !gig.getArtist().equals(artist)){
            gig.setArtist(artist);
        }
        if (venue != null && venue.length() > 0 && !gig.getVenue().equals(venue)){
            gig.setVenue(venue);
        }
        if (location != null && location.length() > 0 && !gig.getLocation().equals(location)){
            gig.setLocation(location);
        }
        if (date != null && !gig.getDate().equals(date)){
            gig.setDate(date);
        }
        if (favourite != gig.isFavourite()){
            gig.setFavourite(favourite);
        }

    }

}
