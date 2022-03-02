package com.sokolov.repository;

import com.sokolov.model.WeatherRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<WeatherRequest, Integer> {
}
