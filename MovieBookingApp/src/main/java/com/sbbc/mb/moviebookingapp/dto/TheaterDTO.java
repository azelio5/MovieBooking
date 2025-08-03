package com.sbbc.mb.moviebookingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TheaterDTO {

    private String name;
    private String location;
    private Integer capacity;
    private String screenType;
}
