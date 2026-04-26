/***
 * receives the HTTP request, checks that pincode and date are provided, calls the service, and returns the response
 * ***/

package com.weather.weatherpincode.controller;

import com.weather.weatherpincode.dto.WeatherResponse;
import com.weather.weatherpincode.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public ResponseEntity<?> getWeather(
            @RequestParam String pincode,
            @RequestParam(required = false, defaultValue = "IN") String country,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate forDate) {

        try {
            WeatherResponse response = weatherService.getWeather(pincode, country, forDate);
            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
