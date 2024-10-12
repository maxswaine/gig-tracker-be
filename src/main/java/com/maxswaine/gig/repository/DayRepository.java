package com.maxswaine.gig.repository;

import com.maxswaine.gig.api.dto.Day;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayRepository extends JpaRepository<Day, String> {
}
