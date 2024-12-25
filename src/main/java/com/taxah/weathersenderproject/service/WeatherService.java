package com.taxah.weathersenderproject.service;

import com.taxah.weathersenderproject.model.weatherEntity.City;
import com.taxah.weathersenderproject.model.weatherEntity.WeatherEntry;
import com.taxah.weathersenderproject.model.weatherEntity.WeatherResponseData;
import com.taxah.weathersenderproject.repository.CityRepository;
import com.taxah.weathersenderproject.repository.WeatherEntryRepository;
import com.taxah.weathersenderproject.repository.WeatherResponseDataRepository;
import lombok.Data;
import org.apache.commons.collections4.keyvalue.DefaultMapEntry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Data
public class WeatherService {

    @Value("${weatherAPI.url}")
    private String url;
    @Value("${weatherAPI.apiKey}")
    private String apiKey;

    private final RestTemplate template;
    private final WeatherResponseDataRepository weatherRepository;
    private final WeatherEntryRepository weatherEntryRepository;
    private final CityRepository cityRepository;

    public WeatherEntry getWeather(String city) {
        String uriString = UriComponentsBuilder.fromUri(URI.create(url))
                .queryParam("place_id", city)
                .queryParam("sections", "current,hourly")
                .queryParam("language", "en")
                .queryParam("units", "auto")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-Key", apiKey);
        headers.set("Accept", "application/*+json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<WeatherResponseData> response = template.exchange(
                uriString,                       // URL запроса
                HttpMethod.GET,            // HTTP метод
                entity,                    // Заголовки запроса
                WeatherResponseData.class  // Класс ожидаемого ответа
        );
        return WeatherEntry.builder()
                .cityName(city)
                .weatherResponseData(response.getBody())
                .build();
    }

    public WeatherResponseData saveWeather(WeatherResponseData weatherData) {
        return weatherRepository.save(weatherData);
    }

    public List<WeatherEntry> findByCreatedDay(LocalDate date) {
        return weatherEntryRepository.findByWeatherResponseDataCreatedDay(date);
    }

    public void saveWeathers(List<WeatherEntry> weathers) {
        weatherEntryRepository.saveAll(weathers);
    }

    @Scheduled(cron = "${weather.cron}")
    public List<WeatherEntry> getDailyWeather() {
        List<WeatherResponseData> weathers = weatherRepository.findByCreatedDay(LocalDate.now());

        List<WeatherEntry> weathersData = new ArrayList<>();
        if (weathers.isEmpty()) {
            List<City> cities = cityRepository.findAll();
            for (City city : cities) {
                weathersData.add(getWeather(city.getName()));
            }
            saveWeathers(weathersData);
        }
        return weathersData;
    }
}
