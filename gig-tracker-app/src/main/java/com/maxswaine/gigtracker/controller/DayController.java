package com.maxswaine.gigtracker.controller;

import com.maxswaine.gigtracker.api.dto.Day;
import com.maxswaine.gigtracker.service.DayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/festival_days")
@RequiredArgsConstructor
public class DayController {

    private final DayService dayService;


    @GetMapping
    public ResponseEntity<List<Day>> getFestivalDays(@PathVariable("festivalId") String festivalId) {
        return new ResponseEntity<>(dayService.getAllDays(festivalId), HttpStatus.OK);
    }

    @PutMapping("/api/day/{dayId}")
    public ResponseEntity<Day> putFestivalDays(
            @PathVariable String festivalId,
            @PathVariable String dayId,
            List<String> artists,
            @RequestParam(name = "userId", required = false) String userId
    ) {
        Day day = dayService.getDayById(dayId);
        if (day == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // Check if the user is the owner of the Festival
        boolean ownsFestival = day.getFestival() != null &&
                day.getFestival().getId().equals(festivalId) &&
                day.getFestival().getUser().getId().equals(userId);
        if (!ownsFestival) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        // Update artists
        if (artists != null && !artists.isEmpty()) {
            List<String> artistsSeen = new ArrayList<>();
            artistsSeen.addAll(day.getArtists());
            artistsSeen.addAll(artists);
            day.setArtists(artistsSeen);
        } else {
            day.setArtists(null);
        }
        return ResponseEntity.ok(day);
    }

}