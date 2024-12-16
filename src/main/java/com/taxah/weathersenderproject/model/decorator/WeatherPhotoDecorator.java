package com.taxah.weathersenderproject.model.decorator;

import com.taxah.weathersenderproject.model.weatherEntity.WeatherResponseData;
import org.telegram.telegrambots.meta.api.objects.InputFile;

public interface WeatherPhotoDecorator {
    InputFile decorate(WeatherResponseData rd);
}
