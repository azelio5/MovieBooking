package com.sbbc.mb.moviebookingapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "booking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)   // foreign key на User
    @JsonBackReference("user-bookings")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id", nullable = false)   // foreign key на Show
    @JsonBackReference("show-bookings")
    private Show show;

    @Column(name = "number_of_seats", nullable = false)
    private Integer numberOfSeats;

    @ElementCollection
    @CollectionTable(
            name = "booking_seats",
            joinColumns = @JoinColumn(name = "booking_id")
    )
    @Column(name = "seat_number", nullable = false)
    private List<String> seatNumbers;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "booking_time", nullable = false)
    private LocalDateTime bookingTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookingStatus status;
}