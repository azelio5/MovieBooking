package com.sbbc.mb.moviebookingapp.repository;

import com.sbbc.mb.moviebookingapp.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShowRepository extends JpaRepository<Show, UUID> {
    Optional<List<Show>> findAllByMovieTitle(String movie);

    Optional<List<Show>> findAllByTheaterName(String theater);
}
