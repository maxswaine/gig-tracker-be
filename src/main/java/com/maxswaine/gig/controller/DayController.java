package com.maxswaine.gig.controller;

import com.maxswaine.gig.api.dto.Day;
import com.maxswaine.gig.service.DayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/festival_days")
@RequiredArgsConstructor
public class DayController {

    private final DayService dayService;

    @GetMapping
    public ResponseEntity<List<Day>> getFestivalDays(@PathVariable("festivalId") String id){
        // TODO
    }
}
