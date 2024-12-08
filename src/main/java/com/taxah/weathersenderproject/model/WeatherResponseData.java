package com.taxah.weathersenderproject.model;

import com.taxah.weathersenderproject.model.weatherObjects.CurrentWeather;
import com.taxah.weathersenderproject.model.weatherObjects.HourlyWeather;
import lombok.Data;

import java.util.List;

@Data
public class WeatherResponseData {
    private String lat;
    private String lon;
    private int elevation;
    private String timezone;
    private String units;
    private CurrentWeather current;
    private HourlyWeather hourly;
}
