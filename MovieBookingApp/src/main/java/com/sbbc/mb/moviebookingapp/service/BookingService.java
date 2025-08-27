package com.sbbc.mb.moviebookingapp.service;

import com.sbbc.mb.moviebookingapp.dto.BookingDTO;
import com.sbbc.mb.moviebookingapp.entity.Booking;
import com.sbbc.mb.moviebookingapp.entity.BookingStatus;
import com.sbbc.mb.moviebookingapp.entity.Show;
import com.sbbc.mb.moviebookingapp.entity.User;
import com.sbbc.mb.moviebookingapp.exception.InvalidOperationException;
import com.sbbc.mb.moviebookingapp.exception.NotFoundException;
import com.sbbc.mb.moviebookingapp.repository.BookingRepository;
import com.sbbc.mb.moviebookingapp.repository.ShowRepository;
import com.sbbc.mb.moviebookingapp.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ShowRepository showRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, ShowRepository showRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.showRepository = showRepository;
        this.userRepository = userRepository;
    }

    public Booking addBooking(BookingDTO bookingDTO) {
        // Получаем username из токена
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found: " + username));

        Show show = showRepository.findById(bookingDTO.getShowId())
                .orElseThrow(() -> new NotFoundException("Show not found with id " + bookingDTO.getShowId()));

        if (!isSeatsAvailable(show.getId(), bookingDTO.getNumberOfSeats())) {
            throw new InvalidOperationException("No seats available in amount of request: " + bookingDTO.getNumberOfSeats());
        }
        if (bookingDTO.getSeatNumbers().size() != bookingDTO.getNumberOfSeats()) {
            throw new InvalidOperationException("Seat number and number of seats do not match");
        }

        validateDuplicateSeats(show.getId(), bookingDTO.getSeatNumbers());

        Booking booking = Booking.builder()
                .user(user) // ← из токена
                .show(show)
                .numberOfSeats(bookingDTO.getNumberOfSeats())
                .seatNumbers(bookingDTO.getSeatNumbers())
                .price(calculateTotalAmount(show.getPrice(), bookingDTO.getNumberOfSeats()))
                .bookingTime(LocalDateTime.now())
                .status(BookingStatus.PENDING)
                .build();

        return bookingRepository.save(booking);
    }

    private Double calculateTotalAmount(Double price, Integer numberOfSeats) {
        return price * numberOfSeats;
    }

    private void validateDuplicateSeats(UUID showId, List<String> requestedSeats) {
        if (requestedSeats == null || requestedSeats.isEmpty()) {
            throw new InvalidOperationException("No seat numbers provided for booking.");
        }

        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new NotFoundException("Show not found with id " + showId));

        Set<String> alreadyBookedSeats = show.getBookings().stream()
                .filter(booking -> booking.getStatus() != BookingStatus.CANCELLED)
                .flatMap(booking -> booking.getSeatNumbers().stream())
                .collect(Collectors.toSet());

        List<String> duplicateSeats = requestedSeats.stream()
                .filter(alreadyBookedSeats::contains)
                .toList();

        if (!duplicateSeats.isEmpty()) {
            throw new InvalidOperationException("Already booked seats: " + duplicateSeats);
        }
    }

    private boolean isSeatsAvailable(UUID showId, Integer requestedSeats) {
        if (requestedSeats == null || requestedSeats <= 0) {
            throw new IllegalArgumentException("Requested seats must be positive");
        }

        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new NotFoundException("Show not found with id " + showId));

        int totalCapacity = show.getTheater().getCapacity();

        int alreadyBooked = show.getBookings().stream()
                .filter(booking -> booking.getStatus() != BookingStatus.CANCELLED)
                .mapToInt(Booking::getNumberOfSeats)
                .sum();

        int availableSeats = totalCapacity - alreadyBooked;

        return availableSeats >= requestedSeats;
    }

    public List<Booking> getUserBookings(UUID userId) {
        return bookingRepository.findAllByUserId(userId);
    }

    public List<Booking> getShowBookings(UUID showId) {
        return bookingRepository.findAllByShowId(showId);
    }

    public Booking confirmBooking(UUID id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new NotFoundException("Booking not found with id " + id));
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new InvalidOperationException("Booking is not in PENDING status");
        }
        //payment api
        booking.setStatus(BookingStatus.CONFIRMED);
        return bookingRepository.save(booking);
    }

    public Booking cancelBooking(UUID id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new NotFoundException("Booking not found with id " + id));

        validateCancellation(booking);

        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }

    private void validateCancellation(Booking booking) {
        LocalDateTime showTime = booking.getShow().getShowTime();
        LocalDateTime deadLine = showTime.minusHours(2);

        if (LocalDateTime.now().isAfter(deadLine)) {
            throw new InvalidOperationException("Cannot cancel the booking)");
        }

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new InvalidOperationException("Booking already cancelled");
        }
    }

    public List<Booking> getBookingsByStatus(BookingStatus status) {
        if (status == null) {
            throw new InvalidOperationException("Booking status must not be null");
        }

        List<Booking> bookings = bookingRepository.findAllByStatus(status);

        if (bookings.isEmpty()) {
            throw new NotFoundException("No bookings found with status: " + status);
        }

        return bookings;
    }
}
