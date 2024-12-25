package com.taxah.weathersenderproject.model.decorator;

import com.taxah.weathersenderproject.model.weatherEntity.WeatherEntry;
import com.taxah.weathersenderproject.model.weatherEntity.WeatherResponseData;

public interface WeatherTextDecorator {
    String decorate(WeatherEntry weatherEntry);
}
