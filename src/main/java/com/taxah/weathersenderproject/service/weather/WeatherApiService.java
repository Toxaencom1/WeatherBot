package com.taxah.weathersenderproject.service.weather;

import com.taxah.weathersenderproject.model.weatherEntity.WeatherEntry;

public interface WeatherApiService {
    WeatherEntry getWeather(String city);
}
