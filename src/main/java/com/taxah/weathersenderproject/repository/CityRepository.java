package com.taxah.weathersenderproject.repository;

import com.taxah.weathersenderproject.model.weatherEntity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    List<City> findByCountry_Name(String countryName);
}
