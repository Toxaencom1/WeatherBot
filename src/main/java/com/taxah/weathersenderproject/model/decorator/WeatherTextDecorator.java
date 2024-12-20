package com.taxah.weathersenderproject.model.decorator;

import com.taxah.weathersenderproject.model.weatherEntity.City;
import com.taxah.weathersenderproject.model.weatherEntity.WeatherResponseData;

public interface WeatherTextDecorator {
    String decorate(WeatherResponseData rd, String city);
}
