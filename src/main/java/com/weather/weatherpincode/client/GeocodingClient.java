/**
 * A helper that talks to OpenWeather's Geocoding API. You give it a pincode, it gives you back latitude and longitude.
 **/

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


    /**
     * RestTemplate — A Spring class used to make HTTP calls to external APIs. You give it a URL, it makes the GET/POST request and returns the response as a String.
     **/
    private final RestTemplate restTemplate = new RestTemplate();
    /**
     * ObjectMapper — A Jackson library class that converts JSON strings into Java objects (and vice versa). We use readTree() to parse the JSON response from APIs and then navigate using .path("key").
     **/
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * public double[] getLatLong(String pincode) {
     * try {
     * // openWeather Geocoding API - converts pincode to lat/long
     * // can be used for global pincodes
     * String url = "http://api.openweathermap.org/geo/1.0/direct" + "?q=" + pincode + "&limit=1" + "&appid=" + apiKey;
     * <p>
     * String response = restTemplate.getForObject(url, String.class);
     * JsonNode root = objectMapper.readTree(response);
     * <p>
     * if (root.isEmpty() || !root.isArray() || root.size() == 0) {
     * return null;
     * }
     * <p>
     * JsonNode location = root.get(0);
     * double lat = root.get("lat").asDouble();
     * double lon = root.get("lon").asDouble();
     * <p>
     * return new double[]{lat, lon};
     * <p>
     * } catch (Exception e) {
     * return null;
     * }
     * }
     **/

    public class GeoResult {
        public double lat;
        public double lon;
        public String cityName;

        public GeoResult(double lat, double lon, String cityName) {
            this.lat = lat;
            this.lon = lon;
            this.cityName = cityName;
        }
    }

    public GeoResult getLatLong(String pincode, String country) {
        try {
            String url = "http://api.openweathermap.org/geo/1.0/zip" + "?zip=" + pincode + "," + country + "&appid=" + apiKey;
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);

            if (root.has("lat") && root.has("lon")) {
                double lat = root.get("lat").asDouble();
                double lon = root.get("lon").asDouble();
                String cityName = root.path("name").asText(null);
                return new GeoResult(lat, lon, cityName);
            }

        } catch (Exception e) {
        }
        try {
            String fallbackUrl = "http://api.openweathermap.org/geo/1.0/direct" + "?q=" + pincode + "&limit=1" + "&appid=" + apiKey;

            String fallbackResponse = restTemplate.getForObject(fallbackUrl, String.class);
            JsonNode fallbackRoot = objectMapper.readTree(fallbackResponse);

            if (fallbackRoot.isArray() && fallbackRoot.size() > 0) {
                JsonNode location = fallbackRoot.get(0);
                double lat = location.get("lat").asDouble();
                double lon = location.get("lon").asDouble();
                String cityName = location.path("name").asText(null);
                return new GeoResult(lat, lon, cityName);
            }

        } catch (Exception e) {
            System.out.println("Geocoding error: " + e.getMessage());
        }

        return null;
    }

}
