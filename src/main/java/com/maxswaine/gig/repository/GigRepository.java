package com.maxswaine.gig.repository;

import com.maxswaine.gig.api.dto.Gig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GigRepository extends JpaRepository<Gig, Long> {
}
