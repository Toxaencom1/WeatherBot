package com.taxah.weathersenderproject.repository;

import com.taxah.weathersenderproject.model.weatherEntity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
