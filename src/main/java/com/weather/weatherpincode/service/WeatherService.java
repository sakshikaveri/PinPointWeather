package com.weather.weatherpincode.service;

import com.weather.weatherpincode.client.WeatherClient;
import com.weather.weatherpincode.client.GeocodingClient;
import com.weather.weatherpincode.dto.WeatherResponse;
import com.weather.weatherpincode.model.PincodeLocation;
import com.weather.weatherpincode.model.WeatherData;
import com.weather.weatherpincode.repository.PincodeLocationRepository;
import com.weather.weatherpincode.repository.WeatherDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class WeatherService {

    @Autowired
    private PincodeLocationRepository pincodeRepo;

    @Autowired
    private WeatherDataRepository weatherRepo;

    @Autowired
    private GeocodingClient geocodingClient;

    @Autowired
    private WeatherClient weatherClient;

    public WeatherResponse getWeather(String pincode, LocalDate forDate) {


        Optional<PincodeLocation> savedLocation = pincodeRepo.findById(pincode);

        PincodeLocation location;

        if (savedLocation.isPresent()) {
            location = savedLocation.get();
        } else {
            double[] latLon = geocodingClient.getLatLong(pincode);

            if (latLon == null) {
                throw new RuntimeException("Invalid pincode or geocoding failed: " + pincode);
            }

            location = new PincodeLocation();
            location.setPincode(pincode);
            location.setLatitude(latLon[0]);
            location.setLongitude(latLon[1]);
            pincodeRepo.save(location);
        }


        Optional<WeatherData> savedWeather = weatherRepo.findByPincodeAndForDate(pincode, forDate);

        WeatherData weatherData;

        if (savedWeather.isPresent()) {
            weatherData = savedWeather.get();
        } else {
            weatherData = weatherClient.getWeather(
                    pincode, location.getLatitude(), location.getLongitude(), forDate
            );

            if (weatherData == null) {
                throw new RuntimeException("Failed to fetch weather for pincode: " + pincode);
            }

            // Save to DB for next time
            weatherRepo.save(weatherData);
        }

        return buildResponse(location, weatherData, forDate);
    }

    private WeatherResponse buildResponse(PincodeLocation location,
                                          WeatherData data,
                                          LocalDate forDate) {
        WeatherResponse response = new WeatherResponse();
        response.setPincode(location.getPincode());
        response.setForDate(forDate.toString());
        response.setLatitude(location.getLatitude());
        response.setLongitude(location.getLongitude());
        response.setTemperature(data.getTemperature());
        response.setFeelsLike(data.getFeelsLike());
        response.setHumidity(data.getHumidity());
        response.setDescription(data.getDescription());
        response.setWindSpeed(data.getWindSpeed());
        return response;
    }
}
