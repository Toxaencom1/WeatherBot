package com.taxah.weathersenderproject.service;

import com.taxah.weathersenderproject.model.decorator.WeatherPhotoDecorator;
import com.taxah.weathersenderproject.model.decorator.WeatherTextDecorator;
import com.taxah.weathersenderproject.model.subscriberEntity.Subscriber;
import com.taxah.weathersenderproject.model.weatherEntity.City;
import com.taxah.weathersenderproject.model.weatherEntity.Country;
import com.taxah.weathersenderproject.model.weatherEntity.Location;
import com.taxah.weathersenderproject.model.weatherEntity.WeatherResponseData;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.time.LocalDate;
import java.util.List;

@Service
@Data
public class WeatherBotFacade {

    private final SubscriberService subscriberService;
    private final WeatherService weatherService;
    private final WeatherTextDecorator weatherTextDecorator;
    private final WeatherPhotoDecorator weatherPhotoDecorator;


    public void addSubscriber(String firstName, Long chatId) {
        subscriberService.addSubscriber(firstName, chatId);
    }

    public void addSubscriber(Subscriber subscriber) {
        subscriberService.addSubscriber(subscriber);
    }

    public Boolean checkIfSubscriberExists(Long chatId) {
        return subscriberService.checkIfSubscriberExists(chatId);
    }

    public void removeSubscriber(Long chatId) {
        subscriberService.removeSubscriber(chatId);
    }

    public void fetchSubscribers() {
        subscriberService.fetchSubscribers();
    }

    public String decorateText(WeatherResponseData weatherData, String cityName) {
        return weatherTextDecorator.decorate(weatherData, cityName);
    }

    public InputFile decoratePhoto(WeatherResponseData weatherData) {
        return weatherPhotoDecorator.decorate(weatherData);
    }

    public WeatherResponseData saveWeather(WeatherResponseData weatherData) {
        return weatherService.saveWeather(weatherData);
    }

    public void saveWeathers(List<WeatherResponseData> weathers) {
        weatherService.getWeatherRepository().saveAll(weathers);
    }

    public List<WeatherResponseData> findWeatherByCreatedDay(LocalDate date) {
        return weatherService.findByCreatedDay(date);
    }

    public List<Country> getAllCountries() {
        return subscriberService.getAllCountries();
    }

    public boolean isLocationsEmpty() {
        return subscriberService.isLocationsEmpty();
    }

    public void fillLocations() {
        subscriberService.fillLocations();
    }

    public Location getLocation(String country, String city) {
        return subscriberService.getLocation(country, city);
    }

    public List<City> getAllCitiesBYCountryName(String countryName) {
        return subscriberService.getAllCitiesBYCountryName(countryName);
    }

    public List<WeatherResponseData> getDailyWeather() {
        return weatherService.getDailyWeather();
    }

    public List<Subscriber> getSubscribers() {
        return subscriberService.getSubscribers();
    }
}
