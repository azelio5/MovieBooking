package com.sbbc.mb.moviebookingapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "theater")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String location;
    private Integer capacity;
    private String screenType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "theater")
    private List<Show> shows;
}