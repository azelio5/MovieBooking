package com.sbbc.mb.moviebookingapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.BINARY) // Хранение как BINARY(16)
    private UUID id;
    private String title;
    private String description;
    private String genre;
    private LocalDate releaseDate;
    private Integer duration;
    private String language;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie")
    @JsonIgnoreProperties("movie")
    private List<Show> shows;
}