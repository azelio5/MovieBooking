package com.sbbc.mb.moviebookingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovieDTO {

    private String title;
    private String description;
    private String genre;
    private LocalDate releaseDate;
    private Integer duration;
    private String language;
}
