# Weather Info for Pincode API

## Overview
A Spring Boot REST API that returns weather information for a given pincode and date.
Implements smart caching — repeated requests for the same pincode/date are served from DB.

## Tech Stack
- Java 17, Spring Boot 3.2
- Spring Data JPA + Postgresql
- OpenWeather Geocoding API + Current Weather API

## Setup

### Prerequisites
- Java 17+
- Postgresql running locally
- Free API key from openweathermap.org

### Steps
1. Create database: `CREATE DATABASE weatherinfo;`
2. Update `application.properties` with your Postgresql credentials and API key
3. Run: `mvn spring-boot:run`

## API

### Get Weather
GET /api/weather?pincode={pincode}&forDate={YYYY-MM-DD}

**Example:**
GET http://localhost:8080/api/weather?pincode=411014&forDate=2024-10-15

**Response:**
{
    "cityName": "Pune",
    "description": "broken clouds",
    "feelsLike": 31.29,
    "forDate": "2024-10-15",
    "humidity": 22,
    "latitude": 18.5685,
    "longitude": 73.9158,
    "pincode": "411014_IN",
    "temperature": 33.19,
    "windSpeed": 0.9
}

GET http://localhost:8080/api/weather?pincode=SW1A1AA&country=GB&forDate=2026-04-26

**Response:**
{
    "cityName": "London",
    "description": "haze",
    "feelsLike": 6.72,
    "forDate": "2026-04-26",
    "humidity": 88,
    "latitude": 51.501,
    "longitude": -0.1416,
    "pincode": "SW1A1AA_GB",
    "temperature": 8.79,
    "windSpeed": 3.6
}

## Optimization / Caching Logic
- Pincode → lat/long is fetched once and saved in `pincode_location` table
- Weather for pincode+date is fetched once and saved in `weather_data` table
- Subsequent calls for same inputs are served entirely from DB (zero external API calls)

## Assumptions
- Historical weather (past dates) uses current weather data as fallback (OpenWeather free tier limitation)

## Run Tests
mvn test
