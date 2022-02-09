package com.sokolov.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherService {
    private final static String API_KEY = "bab513ef79d6df6c6b4fb4f13eaefa92";
    private final static String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";

    public String get(String city) throws IOException {
        URL url = new URL(BASE_URL + city + "&appid=" + API_KEY);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        StringBuilder result = new StringBuilder();

        connection.setRequestMethod("GET");
        connection.setConnectTimeout(3000);
        connection.setReadTimeout(10000);

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {
            String input;
            while ((input = reader.readLine()) != null) {
                result.append(input);
            }
        }
        connection.disconnect();
        return result.toString();
    }
}
