package com.sbbc.mb.moviebookingapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie_show")
public class Show {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.BINARY) // Хранение как BINARY(16)
    private UUID id;
    private LocalDateTime showTime;
    private Double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonBackReference  // предотвращает сериализацию назад к movie
    private Movie movie;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "theater_id", nullable = false)
    @JsonBackReference  // предотвращает сериализацию назад к theater
    private Theater theater;

    @OneToMany(mappedBy = "show", fetch = FetchType.LAZY)
    private List<Booking> bookings;
}
