package com.taxah.weathersenderproject.service.weather;

import com.taxah.weathersenderproject.model.weatherEntity.City;
import com.taxah.weathersenderproject.model.weatherEntity.WeatherEntry;
import com.taxah.weathersenderproject.repository.CityRepository;
import com.taxah.weathersenderproject.repository.WeatherEntryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherApiService weatherApiService;
    private final WeatherEntryRepository weatherEntryRepository;
    private final CityRepository cityRepository;

    public WeatherEntry getWeather(String city) {
        return weatherApiService.getWeather(city);
    }

    public List<WeatherEntry> findByCreatedDay(LocalDate date) {
        return weatherEntryRepository.findByWeatherResponseData_CreatedDay(date);
    }

    public void saveWeathers(List<WeatherEntry> weathers) {
        weatherEntryRepository.saveAll(weathers);
    }

    public List<WeatherEntry> getDailyWeather() {
        List<WeatherEntry> weathersData = weatherEntryRepository.findByWeatherResponseData_CreatedDay(LocalDate.now());
        if (weathersData.isEmpty()) {
            List<City> cities = cityRepository.findAll();
            for (City city : cities) {
                weathersData.add(getWeather(city.getName()));
            }
            saveWeathers(weathersData);
        }
        return weathersData;
    }
}
