package com.maxswaine.gig.api.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table
public class Gig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String artist;
    private String venue;
    private String location;
    private LocalDate date;
    private boolean favourite;
    private List<Moment> moments;

    public Gig() {
    }

    public Gig(Long id, String artist, String venue, String location, LocalDate date, boolean favourite, List<Moment> moments) {
        this.id = id;
        this.artist = artist;
        this.venue = venue;
        this.location = location;
        this.date = date;
        this.favourite = favourite;
        this.moments = moments;
    }

    public Gig(String artist, String venue, String location, LocalDate date, boolean favourite) {
        this.artist = artist;
        this.venue = venue;
        this.location = location;
        this.date = date;
        this.favourite = favourite;
    }
}
