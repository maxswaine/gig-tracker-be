package com.maxswaine.gigtracker.repository;

import com.maxswaine.gigtracker.api.dto.Festival;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FestivalRepository extends JpaRepository<Festival, String> {

    List<Festival> findFestivalsByUserId(String userId);
}
