package com.taxah.weathersenderproject.service;

import com.taxah.weathersenderproject.model.decorator.WeatherDecorator;
import com.taxah.weathersenderproject.model.subscriberEntity.Subscriber;
import com.taxah.weathersenderproject.model.weatherEntity.WeatherResponseData;
import com.taxah.weathersenderproject.repository.WeatherResponseDataRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class TelegramBotService {

    private final SubscriberService subscribers;
    private final WeatherService weatherService;
    private final WeatherDecorator weatherDecorator;
    private final WeatherResponseDataRepository weatherRepository;


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

    public String decorate(WeatherResponseData weatherData) {
        return weatherDecorator.decorate(weatherData);
    }

    public WeatherResponseData saveWeather(WeatherResponseData weatherData) {
        return weatherRepository.save(weatherData);
    }
}
