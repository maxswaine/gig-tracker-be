package com.maxswaine.gig.api.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Getter
@Setter
@ToString
public class Moment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long gigId;
    private String description;

    public Moment() {
    }

    public Moment(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Moment(String description) {
        this.description = description;
    }
}
