package com.weather.weatherpincode.repository;
import com.weather.weatherpincode.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long>{
    Optional<WeatherData> findByPincodeAndForDate(String pincode, LocalDate forDate);

}
