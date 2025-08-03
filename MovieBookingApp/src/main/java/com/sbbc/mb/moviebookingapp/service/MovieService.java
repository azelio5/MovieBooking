package com.sbbc.mb.moviebookingapp.service;

import com.sbbc.mb.moviebookingapp.dto.MovieDTO;
import com.sbbc.mb.moviebookingapp.entity.Movie;
import com.sbbc.mb.moviebookingapp.exception.NotFoundException;
import com.sbbc.mb.moviebookingapp.repository.MovieRepository;
import org.springframework.stereotype.Service;
import static com.sbbc.mb.moviebookingapp.util.Util.applyIfNotNull;

import java.util.List;
import java.util.UUID;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie addMovie(MovieDTO movieDTO) {

        Movie movie = Movie.builder()
                .title(movieDTO.getTitle())
                .description(movieDTO.getDescription())
                .genre(movieDTO.getGenre())
                .releaseDate(movieDTO.getReleaseDate())
                .duration(movieDTO.getDuration())
                .language(movieDTO.getLanguage())
                .build();

        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> getMoviesByGenre(String genre) {
        return movieRepository.findMoviesByGenre(genre).filter(list -> !list.isEmpty()).orElseThrow(() -> new NotFoundException("Movies not found with genre: " + genre));
    }

    public List<Movie> getMoviesByLanguage(String language) {
        return movieRepository.findMoviesByLanguage(language).filter(list -> !list.isEmpty()).orElseThrow(() -> new NotFoundException("Movies not found of language: " + language));
    }

    public Movie getMovieByTitle(String title) {
        return movieRepository.findMovieByTitle(title).orElseThrow(() -> new NotFoundException("Movie not found with title: " + title));
    }

    public Movie updateMovie(UUID id, MovieDTO movieDTO) {

        Movie existingMovie = movieRepository.findById(id).orElseThrow(() -> new NotFoundException("Movie not found with id: " + id));

        Movie.MovieBuilder builder = existingMovie.toBuilder();

        applyIfNotNull(builder::title, movieDTO.getTitle());
        applyIfNotNull(builder::description, movieDTO.getDescription());
        applyIfNotNull(builder::genre, movieDTO.getGenre());
        applyIfNotNull(builder::language, movieDTO.getLanguage());

        applyIfNotNull(builder::releaseDate, movieDTO.getReleaseDate());
        applyIfNotNull(builder::duration, movieDTO.getDuration());

        Movie updatedMovie = builder.build();
        return movieRepository.save(updatedMovie);
    }

    public void deleteMovie(UUID id) {
    if (!movieRepository.existsById(id)) {
        throw new NotFoundException("Movie not found with id: " + id);
    }
        movieRepository.deleteById(id);
    }


}
