package com.taxah.weathersenderproject.model.weatherObjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.taxah.weathersenderproject.model.enums.WeatherCondition;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HourlyData {
    private LocalDateTime date;
    private String weather;
    private int icon;
    private WeatherCondition summary;
    private double temperature;
    private Wind wind;
    @JsonProperty("cloud_cover")
    private CloudCover cloudCover;
    private Precipitation precipitation;
}
