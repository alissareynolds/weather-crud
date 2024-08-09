package com.example.weather_crud.service;

import com.example.weather_crud.exception.WeatherNotFoundException;
import com.example.weather_crud.models.Weather;
import com.example.weather_crud.repository.WeatherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WeatherService {

    private WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public Weather create(Weather weather) {
        Weather newWeather = new Weather(weather.getTemp(), weather.getIsFahrenheit(), weather.getIsCelsius(), weather.getWindSpeed(), weather.getState());
        return weatherRepository.save(newWeather);
    }

    public List<Weather> getAll() {
        return weatherRepository.findAll();
    }

    public Weather getById(UUID id) {
        Optional<Weather> optionalWeather = weatherRepository.findById(id);
        if (optionalWeather.isEmpty()) {
            throw new WeatherNotFoundException("A weather with id: " + id + " was not found.");
        }
        return optionalWeather.get();
    }

    public Weather getByTemp(Double temp) {
        Optional<Weather> optionalWeather = weatherRepository.findByTemp(temp);
        if (optionalWeather.isEmpty()) {
            throw new WeatherNotFoundException("A weather with temp: " + temp + " was not found.");
        }
        return optionalWeather.get();
    }

    public Weather update(Weather weather, UUID id) {
        Optional<Weather> originalWeather = weatherRepository.findById(id);
        if (originalWeather.isEmpty()) {
            throw new WeatherNotFoundException("A weather with id: " + id + " was not found.");
        }
        Weather updatedWeather = new Weather(id, weather.getTemp(), weather.getIsFahrenheit(), weather.getIsCelsius(), weather.getWindSpeed(), weather.getState());
        return weatherRepository.save(updatedWeather);
     }

    public Weather patch(Weather weather, UUID id) {
        Optional<Weather> originalWeather = weatherRepository.findById(id);
        if (originalWeather.isEmpty()) {
            throw new WeatherNotFoundException("A weather with id: " + id + " was not found.");
        }
        Weather updatedWeather = originalWeather.get();
        if (updatedWeather.getTemp() != null) {
            updatedWeather.setTemp(weather.getTemp());
        }
        if (updatedWeather.getIsFahrenheit() != null) {
            updatedWeather.setIsFahrenheit(weather.getIsFahrenheit());
        }
        if (updatedWeather.getIsCelsius() != null) {
            updatedWeather.setIsCelsius(weather.getIsCelsius());
        }
        if (updatedWeather.getWindSpeed() != null) {
            updatedWeather.setWindSpeed(weather.getWindSpeed());
        }
        if (updatedWeather.getState() != null) {
            updatedWeather.setState(weather.getState());
        }
        return weatherRepository.save(updatedWeather);
    }

    public Weather delete(UUID id) {
        Optional<Weather> optionalWeather = weatherRepository.findById(id);
        if (optionalWeather.isEmpty()) {
            throw new WeatherNotFoundException("A weather with id: " + id + " was not found.");
        }
        weatherRepository.delete(optionalWeather.get());
        return optionalWeather.get();
    }
}
