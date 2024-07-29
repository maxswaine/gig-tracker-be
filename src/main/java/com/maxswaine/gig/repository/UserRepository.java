package com.maxswaine.gig.repository;

import com.maxswaine.gig.api.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
}
