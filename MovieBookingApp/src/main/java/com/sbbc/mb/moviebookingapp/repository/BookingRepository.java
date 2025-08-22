package com.sbbc.mb.moviebookingapp.repository;

import com.sbbc.mb.moviebookingapp.entity.Booking;
import com.sbbc.mb.moviebookingapp.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    List<Booking> findAllByUserId(UUID userId);
    List<Booking> findAllByShowId(UUID showId);
    List<Booking> findAllByStatus(BookingStatus bookingStatus);

}
