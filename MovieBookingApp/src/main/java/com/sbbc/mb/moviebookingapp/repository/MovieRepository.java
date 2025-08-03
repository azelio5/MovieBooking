package com.sbbc.mb.moviebookingapp.repository;

import com.sbbc.mb.moviebookingapp.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {

    Optional<List<Movie>> findMoviesByGenre(String genre);

    Optional<List<Movie>> findMoviesByLanguage(String language);

    Optional<Movie> findMovieByTitle(String title);

}
