package com.taxah.weathersenderproject.repository;

import com.taxah.weathersenderproject.model.weatherEntity.CloudCover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CloudCoverRepository extends JpaRepository<CloudCover, Long> {
}
