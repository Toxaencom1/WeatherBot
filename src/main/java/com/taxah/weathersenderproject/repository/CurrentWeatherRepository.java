package com.taxah.weathersenderproject.repository;

import com.taxah.weathersenderproject.model.weatherEntity.CurrentWeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentWeatherRepository extends JpaRepository<CurrentWeather, Long> {
}
