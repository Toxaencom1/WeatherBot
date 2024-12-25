package com.taxah.weathersenderproject.model.weatherEntity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.repository.EntityGraph;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class WeatherEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cityName;

    @Getter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "weather_response_data_id")
    private WeatherResponseData weatherResponseData;
}
