package com.taxah.weathersenderproject.model.decorator;

import com.taxah.weathersenderproject.model.WeatherResponseData;

public interface WeatherDecorator {
    String decorate(WeatherResponseData rd);
}
