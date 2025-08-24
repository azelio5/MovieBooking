package com.sbbc.mb.moviebookingapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.BINARY) // Хранение как BINARY(16)
    private UUID id;
    private String name;
    private String location;
    private Integer capacity;
    private String screenType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "theater")
    @JsonIgnoreProperties("theater") // разрешаем сериализацию Show
    private List<Show> shows;
}