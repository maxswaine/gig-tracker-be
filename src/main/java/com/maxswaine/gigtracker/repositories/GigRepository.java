package com.maxswaine.gigtracker.repositories;

import com.maxswaine.gigtracker.models.Gig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GigRepository extends JpaRepository<Gig, Long> {
    @Query("SELECT DISTINCT g FROM Gig g LEFT JOIN FETCH g.memorableMoments")
    List<Gig> getAllGigsWithMemorableMoments();


    List<Gig> getAllByArtist(String artist);
}
