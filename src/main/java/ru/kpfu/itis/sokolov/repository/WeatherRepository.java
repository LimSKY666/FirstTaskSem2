package ru.kpfu.itis.sokolov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.sokolov.model.Weather;

public interface WeatherRepository extends JpaRepository<Weather, Integer> {
}
