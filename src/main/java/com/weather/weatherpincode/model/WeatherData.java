package com.weather.weatherpincode.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "weather_data",
        uniqueConstraints = @UniqueConstraint(columnNames = {"pincode", "for_date"}))
// ↑ ensures we never save duplicate rows for same pincode+date

public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pincode", nullable = false, length = 10)
    private String pincode;

    @Column(name = "for_date", nullable = false)
    private LocalDate forDate;

    @Column(name = "temperature")
    private Double temperature;     // in Celsius

    @Column(name = "feels_like")
    private Double feelsLike;

    @Column(name = "humidity")
    private Integer humidity;       // percentage

    @Column(name = "description")
    private String description;     // e.g. "clear sky"

    @Column(name = "wind_speed")
    private Double windSpeed;

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(Double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public LocalDate getForDate() {
        return forDate;
    }

    public void setForDate(LocalDate forDate) {
        this.forDate = forDate;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
