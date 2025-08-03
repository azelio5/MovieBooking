package com.sbbc.mb.moviebookingapp.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ShowDTO {
    private LocalDateTime showTime;
    private Double price;
    private UUID movieId;
    private UUID theaterId ;
}
