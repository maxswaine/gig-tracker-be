package com.maxswaine.gig.api.dto;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
    @UuidGenerator
    @Column(nullable = false, updatable = false)
    private String id;
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
