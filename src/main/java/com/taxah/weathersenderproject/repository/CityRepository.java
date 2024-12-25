package com.taxah.weathersenderproject.repository;

import com.taxah.weathersenderproject.model.weatherEntity.City;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    @EntityGraph(attributePaths = {"country.cities"})
    List<City> findByCountry_Name(String countryName);
}
