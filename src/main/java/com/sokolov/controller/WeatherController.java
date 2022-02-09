package com.sokolov.controller;

import com.sokolov.helper.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
public class WeatherController {

    private final WeatherService weatherService = new WeatherService();

    @GetMapping("/weather")
    public String getCity(@RequestParam Optional<String> city) throws IOException {
        return weatherService.get(city.orElse("Kazan"));
    }
}
