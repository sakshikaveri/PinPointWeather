package com.weather.weatherpincode.repository;

import com.weather.weatherpincode.model.PincodeLocation;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PincodeLocationRepository extends JpaRepository<PincodeLocation, String> {
}
