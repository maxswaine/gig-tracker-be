package com.maxswaine.gigtracker.repository;

import com.maxswaine.gigtracker.api.dto.Moment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MomentRepository extends JpaRepository<Moment, String> {
}
