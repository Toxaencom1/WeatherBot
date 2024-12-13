package com.taxah.weathersenderproject.repository;

import com.taxah.weathersenderproject.model.weatherObjects.Precipitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrecipitationRepository extends JpaRepository<Precipitation, Long> {
}
