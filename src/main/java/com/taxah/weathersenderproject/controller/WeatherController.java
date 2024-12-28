package com.taxah.weathersenderproject.controller;


import com.taxah.weathersenderproject.service.WeatherBotFacade;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Data
@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherBotFacade facade;

    @DeleteMapping("/clear/old/weather/entry")
    public ResponseEntity<String> clearOldWeatherResponseData() {
        facade.deleteAllOtherWeathersByDate(LocalDate.now());
        return ResponseEntity.status(HttpStatus.OK).body("Удаление завершено");
    }
}
