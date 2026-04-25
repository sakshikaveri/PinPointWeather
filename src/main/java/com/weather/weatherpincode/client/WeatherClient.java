package com.weather.weatherpincode.client;

import com.weather.weatherpincode.model.WeatherData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;

@Component
public class WeatherClient {
    @Value("${openweather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WeatherData getWeather(String pincode, double lat, double lon, LocalDate forDate) {
        try {
            // OpenWeather Current Weather API
            // units=metric → temperature in Celsius
            String url = "https://api.openweathermap.org/data/2.5/weather"
                    + "?lat=" + lat
                    + "&lon=" + lon
                    + "&appid=" + apiKey
                    + "&units=metric";

            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);

            // Parse the response fields we care about
            WeatherData data = new WeatherData();
            data.setPincode(pincode);
            data.setForDate(forDate);
            data.setTemperature(root.path("main").path("temp").asDouble());
            data.setFeelsLike(root.path("main").path("feels_like").asDouble());
            data.setHumidity(root.path("main").path("humidity").asInt());
            data.setDescription(root.path("weather").get(0).path("description").asText());
            data.setWindSpeed(root.path("wind").path("speed").asDouble());

            return data;

        } catch (Exception e) {
            return null;
        }
    }
}
