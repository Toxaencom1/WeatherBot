package com.taxah.weathersenderproject.model.weatherObjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.taxah.weathersenderproject.model.enums.WeatherCondition;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "hourly_data")
public class HourlyData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private String weather;
    private int icon;

    @Enumerated(EnumType.STRING)
    private WeatherCondition summary;

    private double temperature;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wind_id")
    private Wind wind;

    @JsonProperty("cloud_cover")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cloud_cover_id")
    private CloudCover cloudCover;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "precipitation_id")
    private Precipitation precipitation;
}
