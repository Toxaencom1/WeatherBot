package com.taxah.weathersenderproject.repository;

import com.taxah.weathersenderproject.model.weatherEntity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
}
