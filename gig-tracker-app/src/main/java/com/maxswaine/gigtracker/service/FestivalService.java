package com.maxswaine.gigtracker.service;

import com.maxswaine.gigtracker.api.dto.Festival;
import com.maxswaine.gigtracker.api.requests.FestivalRequest;
import com.maxswaine.gigtracker.repository.FestivalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FestivalService {

    private static final Logger logger = LoggerFactory.getLogger(FestivalService.class);
    private final FestivalRepository festivalRepository;

    public Festival createFestival(FestivalRequest festivalRequest) {

        Festival newFestival = Festival.builder()
                .festivalName(festivalRequest.getFestivalName())
                .startDate(festivalRequest.getStartDate())
                .endDate(festivalRequest.getEndDate())
                .location(festivalRequest.getLocation())
                .build();

        newFestival.generateFestivalDays();

        festivalRepository.save(newFestival);
        return newFestival;
    }

    public List<Festival> getAllFestivals(String userId) {
        logger.info("Fetching festivals for user {}", userId);
        return festivalRepository.findFestivalsByUserId(userId);
    }
}
