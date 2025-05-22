package com.maxswaine.gig.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "days")
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
