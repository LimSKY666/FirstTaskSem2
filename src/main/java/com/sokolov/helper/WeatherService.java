package com.sokolov.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class WeatherService {
    private final static String API_KEY = "bab513ef79d6df6c6b4fb4f13eaefa92";
    private final static String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    private final static JsonParser jsonParser = new JsonParser();

    public Map<String, String> get(String city) throws IOException {
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
        return jsonParser.parseJson(result.toString());
    }
}

class JsonParser {
    public Map<String, String> parseJson(String json) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);

        int temp = (int) (jsonNode.get("main").get("temp").asDouble() - 273.15);
        int feelsLike = (int) (jsonNode.get("main").get("feels_like").asDouble() - 273.15);

        map.put("temp", String.valueOf(temp));
        map.put("feels_like", String.valueOf(feelsLike));
        map.put("humidity", jsonNode.get("main").get("humidity").asText());
        map.put("wind_speed", jsonNode.get("wind").get("speed").asText());
        map.put("name", jsonNode.get("name").asText());

        return map;
    }
}
