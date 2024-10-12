package com.maxswaine.gig.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString(exclude = "attendees")
@Entity
@Table(name = "gigs")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
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

    @ManyToMany
    @JoinTable(
            name = "gig_attendees",
            joinColumns = @JoinColumn(name = "gig_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore
    private List<User> attendees;

    @OneToMany(mappedBy = "gig", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Moment> moments;
}