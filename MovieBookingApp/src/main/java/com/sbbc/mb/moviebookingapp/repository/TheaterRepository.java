package com.sbbc.mb.moviebookingapp.repository;

import com.sbbc.mb.moviebookingapp.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, UUID> {
    Optional<List<Theater>> findTheaterByLocation(String location);
}
