package com.maxswaine.gig.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "festivals")
@ToString(exclude = "user")
public class Festival {
    @Id
    @UuidGenerator
    @Column(nullable = false, updatable = false)
    private String id;
    private String festivalName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // Link to a single user
    @JsonIgnore // Prevent recursion or circular references in JSON
    private User user;

    @OneToMany(mappedBy = "festival", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Day> days;

    public void generateFestivalDays(){
        long numberOfDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        days = new ArrayList<>();

        for (int i = 0; i < numberOfDays; i++) {
            LocalDate dayDate = startDate.plusDays(i);
            Day day = new Day(i + 1, dayDate);
            days.add(day);
        }
    }

}
