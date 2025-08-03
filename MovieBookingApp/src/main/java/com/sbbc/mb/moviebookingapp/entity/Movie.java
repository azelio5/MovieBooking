package com.sbbc.mb.moviebookingapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "movie")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String title;
    private String description;
    private String genre;
    private LocalDate releaseDate;
    private Integer duration;
    private String language;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie")
    private List<Show> shows;
}