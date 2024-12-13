package com.taxah.weathersenderproject.repository;

import com.taxah.weathersenderproject.model.weatherObjects.HourlyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HourlyDataRepository extends JpaRepository<HourlyData, Long> {
}
