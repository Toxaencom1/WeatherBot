package com.taxah.weathersenderproject.model;

import com.taxah.weathersenderproject.model.weatherObjects.CurrentWeather;
import com.taxah.weathersenderproject.model.weatherObjects.HourlyWeather;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "weather_response_data")
public class WeatherResponseData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lat;
    private String lon;
    private int elevation;
    private String timezone;
    private String units;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "current_weather_id")
    private CurrentWeather current;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "hourly_weather_id")
    private HourlyWeather hourly;

    @Column(name = "created_day")
    @CreationTimestamp
    private LocalDate createdDay;
}
