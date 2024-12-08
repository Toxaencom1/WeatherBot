package com.taxah.weathersenderproject.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class WeatherCurrent {
    private WeatherResponseData weatherResponseData;
}
