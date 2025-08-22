package com.sbbc.mb.moviebookingapp.controller;

import com.sbbc.mb.moviebookingapp.dto.BookingDTO;
import com.sbbc.mb.moviebookingapp.entity.Booking;
import com.sbbc.mb.moviebookingapp.entity.BookingStatus;
import com.sbbc.mb.moviebookingapp.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/addbooking")
    public ResponseEntity<Booking> createBooking(@RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.addBooking(bookingDTO));
    }

    @GetMapping("/getusersbookings/{userId}")
    public ResponseEntity<List<Booking>> getUsersBookings(@PathVariable UUID userId) {
        return ResponseEntity.ok(bookingService.getUserBookings(userId));
    }

    @GetMapping("/getshowbookings/{showId}")
    public ResponseEntity<List<Booking>> geShowsBookings(@PathVariable UUID showId) {
        return ResponseEntity.ok(bookingService.getShowBookings(showId));
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<Booking> confirmBooking(@PathVariable UUID id) {
        return ResponseEntity.ok(bookingService.confirmBooking(id));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Booking> cancelBooking(@PathVariable UUID id) {
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }

    @GetMapping("/getbookingsbystatus/{status}")
    public ResponseEntity<List<Booking>> getBookingByStatus(@PathVariable BookingStatus status) {
        return ResponseEntity.ok(bookingService.getBookingsByStatus(status));
    }
}
