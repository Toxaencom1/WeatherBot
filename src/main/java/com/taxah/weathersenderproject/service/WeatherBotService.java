package com.taxah.weathersenderproject.service;

import com.taxah.weathersenderproject.model.decorator.WeatherPhotoDecorator;
import com.taxah.weathersenderproject.model.decorator.WeatherTextDecorator;
import com.taxah.weathersenderproject.model.weatherEntity.WeatherResponseData;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.time.LocalDate;

@Service
@Data
public class WeatherBotService {

    private final SubscriberService subscribers;
    private final WeatherService weatherService;
    private final WeatherTextDecorator weatherTextDecorator;
    private final WeatherPhotoDecorator weatherPhotoDecorator;


    public void addSubscriber(String firstName, Long chatId) {
        subscribers.addSubscriber(firstName, chatId);
    }

    public void removeSubscriber(Long chatId) {
        subscribers.removeSubscriber(chatId);
    }

    public WeatherResponseData getWeather() {
        return weatherService.getWeather();
    }

    public void fetchSubscribers() {
        subscribers.fetchSubscribers();
    }

    public String decorateText(WeatherResponseData weatherData) {
        return weatherTextDecorator.decorate(weatherData);
    }
    public InputFile decoratePhoto(WeatherResponseData weatherData) {
        return weatherPhotoDecorator.decorate(weatherData);
    }

    public WeatherResponseData saveWeather(WeatherResponseData weatherData) {
        return weatherService.saveWeather(weatherData);
    }

    public WeatherResponseData findByCreatedDay(LocalDate date) {
        return weatherService.findByCreatedDay(date);
    }
}
