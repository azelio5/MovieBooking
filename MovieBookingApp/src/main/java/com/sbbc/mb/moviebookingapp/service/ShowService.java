package com.sbbc.mb.moviebookingapp.service;

import com.sbbc.mb.moviebookingapp.dto.ShowDTO;
import com.sbbc.mb.moviebookingapp.entity.Movie;
import com.sbbc.mb.moviebookingapp.entity.Show;
import com.sbbc.mb.moviebookingapp.entity.Theater;
import com.sbbc.mb.moviebookingapp.exception.NotFoundException;
import com.sbbc.mb.moviebookingapp.repository.MovieRepository;
import com.sbbc.mb.moviebookingapp.repository.ShowRepository;
import com.sbbc.mb.moviebookingapp.repository.TheaterRepository;
import com.sbbc.mb.moviebookingapp.util.Util;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.sbbc.mb.moviebookingapp.util.Util.applyIfNotNull;

@Service
public class ShowService {
    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final TheaterRepository theaterRepository;

    public ShowService(ShowRepository showRepository, MovieRepository movieRepository, TheaterRepository theaterRepository) {
        this.showRepository = showRepository;
        this.movieRepository = movieRepository;
        this.theaterRepository = theaterRepository;
    }

    public Show createShow(ShowDTO showDTO) {

        Movie movie = movieRepository.findById(showDTO.getMovieId()).orElseThrow(() -> new NotFoundException("Movie not found with id: " + showDTO.getMovieId()));
        Theater theater = theaterRepository.findById(showDTO.getTheaterId()).orElseThrow(() -> new NotFoundException("Theater not found with id: " + showDTO.getTheaterId()));

        Show show = Show.builder()
                .showTime(showDTO.getShowTime())
                .price(showDTO.getPrice())
                .movie(movie)
                .theater(theater)
                .build();
        return showRepository.save(show);
    }

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public List<Show> getAllShowsByMovie(String movie) {
        return showRepository.findAllByMovieTitle(movie).filter(list -> !list.isEmpty()).orElseThrow(() -> new NotFoundException("No shows found for movie: " + movie));
    }

    public List<Show> getAllShowsByTheater(String theater) {
        return showRepository.findAllByTheaterName(theater).filter(list -> !list.isEmpty()).orElseThrow(() -> new NotFoundException("No shows found for theater: " + theater));

    }

    public Show updateSHow(UUID showId, ShowDTO showDTO) {

        Movie movie = movieRepository.findById(showDTO.getMovieId()).orElseThrow(() -> new NotFoundException("Movie not found with id: " + showDTO.getMovieId()));
        Theater theater = theaterRepository.findById(showDTO.getTheaterId()).orElseThrow(() -> new NotFoundException("Theater not found with id: " + showDTO.getTheaterId()));
        Show existingShow = showRepository.findById(showId).orElseThrow(() -> new NotFoundException("Show not found with id: " + showId));

        Show.ShowBuilder builder = existingShow.toBuilder();
        applyIfNotNull(builder::movie, movie);
        applyIfNotNull(builder::theater, theater);
        applyIfNotNull(builder::showTime, showDTO.getShowTime());
        applyIfNotNull(builder::price, showDTO.getPrice());

        Show updatedShow = builder.build();

        return showRepository.save(updatedShow);

    }

    public void deleteShow(UUID id) {
        if (!showRepository.existsById(id)) {
            throw new NotFoundException("Show not found with id: " + id);
        }
        showRepository.deleteById(id);
    }
}
