package com.maxswaine.gigtracker.controller;

import com.maxswaine.gigtracker.api.dto.Festival;
import com.maxswaine.gigtracker.api.requests.FestivalRequest;
import com.maxswaine.gigtracker.service.FestivalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/festival")
@RequiredArgsConstructor
public class FestivalController {
    private final FestivalService festivalService;
    //TODO create endpoint that creates festival. Festival will then have the days already calculated
    // Then the user can send a list of artists to each day :)

    @GetMapping(path = "{userId}")
    public ResponseEntity<List<Festival>> getAllFestivals(@PathVariable("userId") String userId){
        return new ResponseEntity<>(festivalService.getAllFestivals(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Festival> addFestival(@RequestBody FestivalRequest festivalRequest){
        Festival newFestival = festivalService.createFestival(festivalRequest);
        return new ResponseEntity<>(newFestival, HttpStatus.CREATED);
    }
}
