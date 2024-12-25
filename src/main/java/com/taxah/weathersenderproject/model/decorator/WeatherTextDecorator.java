package com.taxah.weathersenderproject.model.decorator;

import com.taxah.weathersenderproject.model.weatherEntity.WeatherEntry;

public interface WeatherTextDecorator {
    String decorate(WeatherEntry weatherEntry);
}
