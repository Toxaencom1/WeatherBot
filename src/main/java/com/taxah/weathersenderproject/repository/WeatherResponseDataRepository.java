package com.taxah.weathersenderproject.repository;

import com.taxah.weathersenderproject.model.weatherEntity.WeatherResponseData;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WeatherResponseDataRepository extends JpaRepository<WeatherResponseData, Long> {

    @EntityGraph(attributePaths = {"current.wind", "current.precipitation", "hourly.data.wind", "hourly.data.cloudCover", "hourly.data.precipitation"})
    List<WeatherResponseData> findByCreatedDay(LocalDate date);
}
