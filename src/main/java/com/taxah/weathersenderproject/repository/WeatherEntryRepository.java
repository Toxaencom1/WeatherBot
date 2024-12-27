package com.taxah.weathersenderproject.repository;

import com.taxah.weathersenderproject.model.weatherEntity.WeatherEntry;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WeatherEntryRepository extends JpaRepository<WeatherEntry, Long> {

    @EntityGraph(attributePaths = {"weatherResponseData.hourly.data.wind", "weatherResponseData.hourly.data.cloudCover", "weatherResponseData.hourly.data.precipitation"})
    List<WeatherEntry> findByWeatherResponseDataCreatedDay(LocalDate date);

    @Modifying
    @Transactional
    @Query("DELETE FROM WeatherEntry e WHERE e.weatherResponseData.createdDay <= :yesterday")
    void deleteAllByDateBeforeOrEqual(@Param("yesterday") LocalDate yesterday);
}
