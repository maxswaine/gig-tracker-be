package com.maxswaine.gigtracker.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@Entity
@Table(name = "moments")
@ToString(exclude = "gig")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Moment {
    @Id
    @UuidGenerator
    private String id;
    private String description;

    @ManyToOne
    @JoinColumn(name = "gig_id")
    @JsonIgnore
    private Gig gig;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
