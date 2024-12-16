package com.taxah.weathersenderproject.repository;

import com.taxah.weathersenderproject.model.weatherEntity.WeatherResponseData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WeatherResponseDataRepository extends JpaRepository<WeatherResponseData, Long> {

    List<WeatherResponseData> findByCreatedDayAndTimezoneContainingIgnoreCase(LocalDate now, String timeZone);
}
