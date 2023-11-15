package com.maxswaine.gigtracker.repositories;

import com.maxswaine.gigtracker.models.Moment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MomentRepository extends JpaRepository<Moment, Long> {
}
