package com.sokolov.controller;

import com.sokolov.model.WeatherJournal;
import com.sokolov.model.WeatherRequest;
import com.sokolov.repository.RequestRepository;
import com.sokolov.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

@RestController
public class RequestController {

    private final RequestRepository requestRepository;
    private final WeatherRepository weatherRepository;

    @Autowired
    public RequestController(RequestRepository requestRepository, WeatherRepository weatherRepository) {
        this.requestRepository = requestRepository;
        this.weatherRepository = weatherRepository;
    }

    @GetMapping("/requestsID")
    public ArrayList<WeatherRequest> getRequestsByUser (@RequestParam int id) throws IOException {
        List<WeatherRequest> requests = requestRepository.findAll();
        ArrayList<WeatherRequest> result = new ArrayList<>();

        for (WeatherRequest request : requests) {
            if (request.getUser().getId().equals(id)) {
                result.add(request);
            }
        }
        return result;
    }

    @GetMapping("/requestsCity")
    public ArrayList<WeatherRequest> getRequestsByCity (@RequestParam String city) throws IOException  {
        List<WeatherRequest> requestList = requestRepository.findAll();
        ArrayList<WeatherRequest> result = new ArrayList<>();
        for (WeatherRequest request : requestList) {
            if (request.getCity().equals(city)) {
               result.add(request);
            }
        }
        return result;
    }

    @GetMapping("/weatherJournalCity")
    public ArrayList<WeatherJournal> getAllWeatherByCity(@RequestParam String city) {
        List<WeatherJournal> weatherList = weatherRepository.findAll();
        ArrayList<WeatherJournal> result = new ArrayList<>();
        for (WeatherJournal weather : weatherList) {
            if (weather.getName().equals(city)) {
                result.add(weather);
            }
        }
        return result;
    }
}
