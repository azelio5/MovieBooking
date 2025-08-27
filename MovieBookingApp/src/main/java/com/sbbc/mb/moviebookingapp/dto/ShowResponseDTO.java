package com.sbbc.mb.moviebookingapp.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ShowResponseDTO {
    private LocalDateTime showTime;
    private Double price;
    private String movieTitle;
    private String movieGenre;
    private String movieDescription;
    private Integer movieDuration;
    private String movieLanguage;
    private String theaterName;
    private String theaterLocation;
}