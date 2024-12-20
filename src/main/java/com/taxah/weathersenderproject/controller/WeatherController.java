package com.taxah.weathersenderproject.controller;


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
        WeatherResponseData weather = service.getWeather("Almaty");
        if (weather != null) {
            System.out.println("Weather gathered!!! " + LocalDateTime.now());
        }
        return weather;
    }
}
