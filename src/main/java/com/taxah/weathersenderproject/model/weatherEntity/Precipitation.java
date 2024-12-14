package com.taxah.weathersenderproject.model.weatherEntity;

import com.taxah.weathersenderproject.model.enums.PrecipitationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Precipitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double total;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrecipitationType type;
}
