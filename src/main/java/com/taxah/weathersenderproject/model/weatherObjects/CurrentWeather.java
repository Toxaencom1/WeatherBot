package com.taxah.weathersenderproject.model.weatherObjects;

import lombok.Data;

@Data
public class CurrentWeather {
    private String icon;
    private int iconNum;
    private String summary;
    private double temperature;
    private Wind wind;
    private Precipitation precipitation;
    private int cloudCover;
}
