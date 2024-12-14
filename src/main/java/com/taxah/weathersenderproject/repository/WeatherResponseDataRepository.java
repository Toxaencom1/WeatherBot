package com.taxah.weathersenderproject.repository;

import com.taxah.weathersenderproject.model.weatherEntity.WeatherResponseData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface WeatherResponseDataRepository extends JpaRepository<WeatherResponseData, Long> {
    WeatherResponseData findByCreatedDay(LocalDate now);
}
