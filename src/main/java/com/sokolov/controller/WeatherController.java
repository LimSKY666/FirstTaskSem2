package com.sokolov.controller;

import com.sokolov.helper.WeatherHelper;
import com.sokolov.model.User;
import com.sokolov.model.WeatherJournal;
import com.sokolov.repository.UserRepository;
import com.sokolov.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

@RestController
public class WeatherController {

    private final WeatherHelper weatherHelper = new WeatherHelper();

    private final WeatherRepository weatherRepository;

    private final UserRepository userRepository;

    @Autowired
    public WeatherController(WeatherRepository weatherRepository, UserRepository userRepository) {
        this.weatherRepository = weatherRepository;
        this.userRepository = userRepository;
    }


    @GetMapping("/weather")
    public WeatherJournal getWeather(@RequestParam Optional<String> city, @RequestParam String email) throws IOException {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                Map<String, String> result = weatherHelper.get(city.orElse("Kazan"));
                return weatherRepository.save(new WeatherJournal(result.get("temp"), result.get("feels_like"), result.get("humidity"), result.get("wind_speed"), result.get("name"), new Date(), email));
            }
        }
        return null;
    }

    @GetMapping("/weatherAll")
    public Iterable<WeatherJournal> getAll() {
        return new ArrayList<>(weatherRepository.findAll());
    }
}
