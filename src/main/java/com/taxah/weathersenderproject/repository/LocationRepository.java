package com.taxah.weathersenderproject.repository;

import com.taxah.weathersenderproject.model.weatherEntity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByCountry_NameAndCity_Name(String countryName, String cityName);
}
