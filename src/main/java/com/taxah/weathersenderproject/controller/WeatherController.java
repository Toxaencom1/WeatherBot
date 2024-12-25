package com.taxah.weathersenderproject.controller;


import com.taxah.weathersenderproject.model.weatherEntity.WeatherEntry;
import com.taxah.weathersenderproject.model.weatherEntity.WeatherResponseData;
import com.taxah.weathersenderproject.service.WeatherService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Data
@RestController
@RequestMapping
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService service;

    @RequestMapping("/get")
    public WeatherResponseData getWeather() {
        WeatherEntry weather = service.getWeather("Almaty");
        if (weather.getWeatherResponseData() != null) {
            System.out.println("Weather gathered!!! " + LocalDateTime.now());
        }
        return weather.getWeatherResponseData();
    }
}
