package com.taxah.weathersenderproject.service;

import com.taxah.weathersenderproject.model.subscriberEntity.Subscriber;
import com.taxah.weathersenderproject.model.weatherEntity.City;
import com.taxah.weathersenderproject.model.weatherEntity.Country;
import com.taxah.weathersenderproject.model.weatherEntity.Location;
import com.taxah.weathersenderproject.repository.CityRepository;
import com.taxah.weathersenderproject.repository.CountryRepository;
import com.taxah.weathersenderproject.repository.LocationRepository;
import com.taxah.weathersenderproject.repository.SubscriberRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

@Data
@Service
@RequiredArgsConstructor
public class SubscriberService implements Iterable<Subscriber> {

    private List<Subscriber> subscribers = new ArrayList<>();
    private final SubscriberRepository subscriberRepository;
    private final CountryRepository countryRepository;
    private final LocationRepository locationRepository;
    private final CityRepository cityRepository;

    public void addSubscriber(String name, Long chatId) {
        Subscriber subscriber = new Subscriber(name, chatId);
        subscribers.add(subscriber);
        subscriberRepository.save(subscriber);
    }

    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
        subscriberRepository.save(subscriber);
    }

    public void removeSubscriber(Long chatId) {
        subscribers.removeIf(subscriber -> subscriber.getChatId().equals(chatId));
        subscriberRepository.deleteByChatId(chatId);
    }

    @Override
    public Iterator<Subscriber> iterator() {
        return subscribers.iterator();
    }

    @Override
    public void forEach(Consumer<? super Subscriber> action) {
        Iterable.super.forEach(action);
    }

    public boolean isEmpty() {
        return subscribers.isEmpty();
    }

    public void fetchSubscribers() {
        subscribers = subscriberRepository.findAll();
    }

    public Boolean checkIfSubscriberExists(Long chatId) {
        List<Subscriber> byChatId = subscriberRepository.findByChatId(chatId);
        return !byChatId.isEmpty();
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public boolean isLocationsEmpty() {
        return locationRepository.count() == 0;
    }

    public void fillLocations() {
        List<Country> countries = countryRepository.findAll();
        List<Location> locations = new ArrayList<>();
        for (Country country : countries) {
            for (City city : country.getCities()) {
                locations.add(
                        Location.builder()
                                .country(country)
                                .city(city)
                                .build()
                );
            }
        }
        locationRepository.saveAll(locations);
    }

    public Location getLocation(String country, String city) {
        return locationRepository.findByCountry_NameAndCity_Name(country, city).orElse(null);
    }

    public List<City> getAllCitiesBYCountryName(String countryName) {
        return cityRepository.findByCountry_Name(countryName);
    }
}
