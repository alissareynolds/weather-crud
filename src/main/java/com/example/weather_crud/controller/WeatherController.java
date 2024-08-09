package com.example.weather_crud.controller;

import com.example.weather_crud.exception.WeatherNotFoundException;
import com.example.weather_crud.models.Weather;
import com.example.weather_crud.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping
    public ResponseEntity<Weather> createWeather(@RequestBody Weather weather) {
        Weather newWeather = weatherService.create(weather);
        return new ResponseEntity<>(weather, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Weather>> getAllWeather() {
        List<Weather> weatherList = weatherService.getAll();
        return new ResponseEntity<>(weatherList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Weather> getWeatherById(@PathVariable UUID id) {
        Weather weather;
        try {
            weather = weatherService.getById(id);
        } catch (WeatherNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(weather, HttpStatus.OK);
    }

    @GetMapping("/temp/{temp}")
    public ResponseEntity<Weather> getWeatherByTemp(@PathVariable Double temp) {
        Weather weather;
        try {
            weather = weatherService.getByTemp(temp);
        } catch (WeatherNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(weather, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Weather> updateWeather(@RequestBody Weather weather, @PathVariable UUID id) {
        Weather newWeather;
        try {
            newWeather = weatherService.update(weather, id);
        } catch (WeatherNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newWeather, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Weather> patchWeather(@RequestBody Weather weather, @PathVariable UUID id) {
        Weather newWeather;
        try {
            newWeather = weatherService.patch(weather, id);
        } catch (WeatherNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newWeather, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Weather> deleteWeather(@PathVariable UUID id) {
        Weather weather;
        try {
            weather = weatherService.delete(id);
        } catch (WeatherNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(weather, HttpStatus.OK);
    }
 }
