package com.maxswaine.gigtracker;

import com.maxswaine.gigtracker.models.Gig;
import com.maxswaine.gigtracker.repositories.GigRepository;
import com.maxswaine.gigtracker.repositories.MomentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GigService {
    @Autowired
    GigRepository gigRepository;

    @Autowired
    MomentRepository momentRepository;


    // CREATE
    public Gig addGig(Gig gig) {
        Gig newGig = gigRepository.save(gig);
        System.out.println(newGig);
        return newGig;
    }

    // READ

    public List<Gig> getAllGigs(int limit) {
        return gigRepository
                .findAll()
                .stream()
                .limit(limit)
                .collect(Collectors.toList());
    }
    public List<Gig> getGigsByArtist(String artist, int limit) {
        List<Gig> gigs = gigRepository.getAllByArtist(artist);

        return gigs.stream().limit(limit).collect(Collectors.toList());
    }
    public Gig getGigsById(long id) {
      return gigRepository.findById(id).orElseThrow(GigNotFoundException::new);
    }

    // UPDATE


    // DELETE
    @Transactional
    public void deleteGreetingById(long id) {
        if (!gigRepository.existsById(id)) {
            throw new GigNotFoundException();
        }

        gigRepository.deleteById(id);
    }


}