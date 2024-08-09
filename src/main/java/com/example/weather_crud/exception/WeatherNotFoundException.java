package com.example.weather_crud.exception;

public class WeatherNotFoundException extends RuntimeException{
    public WeatherNotFoundException(String message) {
        super(message);
    }
}
