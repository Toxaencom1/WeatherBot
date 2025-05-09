package com.taxah.weathersenderproject.repository;

import com.taxah.weathersenderproject.model.weatherEntity.Wind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WindRepository extends JpaRepository<Wind, Long> {
}
