package com.maxswaine.gig.service;

import com.maxswaine.gig.api.dto.Day;
import com.maxswaine.gig.repository.DayRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DayService {

    private static final Logger logger = LoggerFactory.getLogger(DayService.class);
    private final DayRepository dayRepository;

    public List<Day> getAllDays(String festivalId) {
        logger.info("Fetching days for festival {}", festivalId);
        return dayRepository.findAllDaysByFestivalId(festivalId);
    }

    public Day getDayById(String dayId){
        return dayRepository.findById(dayId)
                .map(day -> {
                    logger.info("Found day with id {}: ", dayId);
                    return day;
                })
                .orElseThrow(() -> {
                    logger.error("Day not found with id {}", dayId);
                    return new EntityNotFoundException(dayId);
                });
    }
}
