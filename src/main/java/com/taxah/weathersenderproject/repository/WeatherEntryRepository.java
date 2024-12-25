package com.taxah.weathersenderproject.repository;

import com.taxah.weathersenderproject.model.weatherEntity.WeatherEntry;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WeatherEntryRepository extends JpaRepository<WeatherEntry, Long> {

    @EntityGraph(attributePaths = {"weatherResponseData.hourly.data.wind", "weatherResponseData.hourly.data.cloudCover", "weatherResponseData.hourly.data.precipitation"})
    List<WeatherEntry> findByWeatherResponseDataCreatedDay(LocalDate date);
}
