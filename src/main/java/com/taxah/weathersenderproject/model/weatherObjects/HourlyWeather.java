package com.taxah.weathersenderproject.model.weatherObjects;

import lombok.Data;

import java.util.List;

@Data
public class HourlyWeather {
    private List<HourlyData> data;
}
