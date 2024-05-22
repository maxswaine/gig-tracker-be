package com.maxswaine.gig.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Moment {
    @Id
    @UuidGenerator
    private String id;
    private String description;

    @ManyToOne
    @JoinColumn(name = "gig_id")
    @JsonIgnore
    private Gig gig;

    public Moment(String description) {
        this.description = description;
    }
}
