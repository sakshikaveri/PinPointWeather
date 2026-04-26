/**
 * Interfaces that extend JpaRepository. Spring automatically writes all the DB code for us — we just define method names like findByPincodeAndForDate and Spring knows what SQL to generate.
 **/

package com.weather.weatherpincode.repository;

import com.weather.weatherpincode.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
    Optional<WeatherData> findByPincodeAndForDate(String pincode, LocalDate forDate);

}
