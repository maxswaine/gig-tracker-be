package com.maxswaine.gigtracker.repository;

import com.maxswaine.gigtracker.api.dto.Day;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DayRepository extends JpaRepository<Day, String> {


    List<Day> findAllDaysByFestivalId(String festivalId);

}
