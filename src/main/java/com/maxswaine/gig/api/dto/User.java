package com.maxswaine.gig.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
@ToString(exclude = {"gigs", "friends"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {

    @Id
    @UuidGenerator
    @Column(nullable = false, updatable = false)
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String password;
    private Role role;

    @ManyToMany
    @JoinTable(
            name = "friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    @JsonIgnore
    private List<User> friends;

    @ManyToMany(mappedBy = "attendees")
    @JsonIgnore
    private List<Gig> gigs;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role);
    }

}