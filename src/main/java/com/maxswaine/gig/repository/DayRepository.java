package com.maxswaine.gig.repository;

import com.maxswaine.gig.api.dto.Day;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DayRepository extends JpaRepository<Day, String> {


    List<Day> findAllDaysByFestivalId(String festivalId);

}
