package com.maxswaine.gig.api.dto;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Getter
@Setter
@Entity
@Table
@ToString(exclude = "gigs")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @UuidGenerator
    @Column(nullable = false, updatable = false)
    private String id;

    private String email;
    private String username;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Gig> gigs;

}
