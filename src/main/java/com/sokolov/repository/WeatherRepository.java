package com.sokolov.repository;

import com.sokolov.model.WeatherJournal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<WeatherJournal, Integer> {
}
