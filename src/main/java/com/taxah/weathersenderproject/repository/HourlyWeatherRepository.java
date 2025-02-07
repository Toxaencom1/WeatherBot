package com.taxah.weathersenderproject.repository;

import com.taxah.weathersenderproject.model.weatherEntity.HourlyWeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HourlyWeatherRepository extends JpaRepository<HourlyWeather, Long> {
}
