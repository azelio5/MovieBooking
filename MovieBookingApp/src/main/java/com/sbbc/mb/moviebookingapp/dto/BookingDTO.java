package com.sbbc.mb.moviebookingapp.dto;

import com.sbbc.mb.moviebookingapp.entity.BookingStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class BookingDTO {
    private Integer numberOfSeats;
    private LocalDateTime bookingTime;
    private Double price;
    private BookingStatus status;
    private List<String> seatNumbers;
    private UUID userId;
    private UUID showId;

}
