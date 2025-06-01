package com.maxswaine.gigtracker.api.requests;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FestivalRequest {
    private String festivalName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
}
