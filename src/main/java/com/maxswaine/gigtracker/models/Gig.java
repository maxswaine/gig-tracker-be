package com.maxswaine.gigtracker.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "gigs")
public class Gig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String artist;
    private String venue;
    private Date date;
    private String location;
    private Boolean favourite;

    @OneToMany(mappedBy = "gig", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("gig")
    private List<Moment> memorableMoments;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }

    public List<Moment> getMemorableMoments() {
        return memorableMoments;
    }

    public void setMemorableMoments(List<Moment> moments) {
        this.memorableMoments = moments;
    }
}
