package ru.kpfu.itis.sokolov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.sokolov.model.Request;
import ru.kpfu.itis.sokolov.model.User;
import ru.kpfu.itis.sokolov.model.Weather;
import ru.kpfu.itis.sokolov.repository.RequestRepository;
import ru.kpfu.itis.sokolov.repository.UserRepository;
import ru.kpfu.itis.sokolov.repository.WeatherRepository;
import ru.kpfu.itis.sokolov.service.WeatherService;
import ru.kpfu.itis.sokolov.model.Weather;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class WeatherController {
    private final WeatherService weatherService = new WeatherService();
    private final WeatherRepository weatherRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;


    @Autowired
    public WeatherController(WeatherRepository weatherRepository, UserRepository userRepository, RequestRepository requestRepository) {
        this.weatherRepository = weatherRepository;
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
    }



    @GetMapping("/weather")
    public Weather addWeather(@Valid @RequestParam Optional<String> city, @RequestParam int user_id) throws IOException {
        Map<String, String> result = weatherService.get(city.orElse("Kazan"));
        Weather weather = new Weather(result.get("name"), result.get("temp"), result.get("feels_like"), result.get("wind_speed"), new Date().getTime());
        requestRepository.save(new Request(userRepository.getById(user_id), weather, result.get("name")));
        return weatherRepository.save(weather);
    }

    @GetMapping("/getAllRequestById")
    public String getAllRequestByID(@RequestParam int id) {
        List<Request> requestList = requestRepository.findAll();
        String result = "";
        for (Request request : requestList) {
            if (request.getUser().getId() == id) {
                result += request.toString() + "\n";
            }
        }
        return result;
    }

    @GetMapping("/getAllRequestByCity")
    public String getAllRequestByCity(@RequestParam String city) {
        List<Request> requestList = requestRepository.findAll();
        String result = "";
        for (Request request : requestList) {
            if (request.getCity().equals(city)) {
                result += request.toString() + "\n";
            }
        }
        return result;
    }

    @GetMapping("/getAllWeatherByCity")
    public String getAllWeatherByCity(@RequestParam String city) {
        List<Weather> weatherList = weatherRepository.findAll();
        String result = "";
        for (Weather weather : weatherList) {
            if (weather.getName().equals(city)) {
                result += weather.toString() + "\n";
            }
        }
        return result;
    }

    @GetMapping("/getWeather")
    public String getWeather(@RequestParam String email) {
        List<User> users = userRepository.findAll();
        List<Weather> weatherList = weatherRepository.findAll();
        String result = "";
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                for (Weather weather : weatherList) {
                    result += weather.toString() + "\n";
                }
            }
        }
        return result;
    }

    @GetMapping("/getAll")
    public String getAll(){
        List<User> users = userRepository.findAll();
        List<Weather> weatherList = weatherRepository.findAll();
        String result = "";
        for (User user : users) {
            result += user.toString() + "\n";
        }
        for (Weather weather : weatherList) {
            result += weather.toString()+"\n";
        }
        return result;
    }
}
