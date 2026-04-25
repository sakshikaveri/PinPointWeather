package com.weather.weatherpincode.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pincode_location")

public class PincodeLocation {

    @Id
    @Column(name = "pincode", length = 10)
    private String pincode;  //primary key

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    @Column(name = "city_name")
    private String cityName;

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
