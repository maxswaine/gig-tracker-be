package com.maxswaine.gig.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class Day {
    @Id
    @UuidGenerator
    private String id;
    private int dayNumber;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "festival_id")
    @JsonIgnore
    private Festival festival;

    public Day(int dayNumber, LocalDate date) {
        this.dayNumber = dayNumber;
        this.date = date;
    }

    private List<String> artists;
}
