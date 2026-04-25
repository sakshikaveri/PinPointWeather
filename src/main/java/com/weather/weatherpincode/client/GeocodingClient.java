package com.weather.weatherpincode.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class GeocodingClient {
    @Value("${openweather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public double[] getLatLong(String pincode) {
        try {
            // OpenWeather Geocoding API — converts pincode to lat/long
            // "IN" = India country code
            String url = "http://api.openweathermap.org/geo/1.0/zip"
                    + "?zip=" + pincode + ",IN"
                    + "&appid=" + apiKey;

            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);

            double lat = root.get("lat").asDouble();
            double lon = root.get("lon").asDouble();

            return new double[]{lat, lon};

        } catch (Exception e) {
            return null;
        }
    }
}
