package com.taxah.weathersenderproject.model.weatherEntity;

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
@Table(name = "current_weather")
public class CurrentWeather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String icon;
    private int iconNum;
    private String summary;
    private double temperature;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wind_id")
    private Wind wind;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn
    private Precipitation precipitation;
    private int cloudCover;
}
