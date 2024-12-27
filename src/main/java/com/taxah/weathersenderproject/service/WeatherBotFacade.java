package com.taxah.weathersenderproject.service;

import com.taxah.weathersenderproject.model.decorator.WeatherPhotoDecorator;
import com.taxah.weathersenderproject.model.decorator.WeatherTextDecorator;
import com.taxah.weathersenderproject.model.subscriberEntity.Subscriber;
import com.taxah.weathersenderproject.model.weatherEntity.*;
import com.taxah.weathersenderproject.repository.WeatherEntryRepository;
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
    private final WeatherEntryRepository weatherEntryRepository;

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

    public String decorateText(WeatherEntry weatherEntry) {
        return weatherTextDecorator.decorate(weatherEntry);
    }

    public InputFile decoratePhoto(WeatherResponseData weatherData) {
        return weatherPhotoDecorator.decorate(weatherData);
    }

    public void saveWeathers(List<WeatherEntry> weathers) {
        weatherService.saveWeathers(weathers);
    }

    public List<WeatherEntry> findWeatherByCreatedDay(LocalDate date) {
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

    public List<WeatherEntry> getDailyWeather() {
        return weatherService.getDailyWeather();
    }

    public List<Subscriber> getSubscribers() {
        return subscriberService.getSubscribers();
    }

    public void deleteAllByDateBeforeOrEqual(LocalDate date) {
        weatherEntryRepository.deleteAllByDateBeforeOrEqual(date);

    }
}
