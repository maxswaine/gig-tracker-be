package com.maxswaine.gig.repository;

import com.maxswaine.gig.api.dto.Gig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GigRepository extends JpaRepository<Gig, Long> {

    List<Gig> findGigsByArtistContainingIgnoreCase(String artist);

    List<Gig> findGigByFavourite(boolean favourite);

    List<Gig> findGigsByVenueContainingIgnoreCase(String venue);
}
