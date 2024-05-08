package com.maxswaine.gig.api.dto;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Gig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String artist;
    private String venue;
    private String location;
    private LocalDateTime date;
    private boolean favourite;

    @OneToMany(mappedBy = "gig", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Moment> moments;

    public Gig(String artist, String venue, String location, LocalDateTime date, boolean favourite) {
        this.artist = artist;
        this.venue = venue;
        this.location = location;
        this.date = date;
        this.favourite = favourite;
    }
}
