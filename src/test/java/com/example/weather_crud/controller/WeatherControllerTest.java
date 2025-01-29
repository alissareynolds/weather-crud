package com.example.weather_crud.controller;

import com.example.weather_crud.exception.WeatherNotFoundException;
import com.example.weather_crud.models.Weather;
import com.example.weather_crud.models.WeatherState;
import com.example.weather_crud.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WeatherControllerTest {

    private WeatherController weatherController;
    private WeatherService mockWeatherService;

    public final Weather input = new Weather(null, 72.5, true, false, 12, WeatherState.PARTLY_CLOUDY);
    public final Weather input2 = new Weather(null, 15.5, false, true, 6, WeatherState.CLOUDY);
    public final Weather recordWithId = new Weather(UUID.randomUUID(), 72.5, true, false, 12, WeatherState.PARTLY_CLOUDY);
    public final Weather recordWithId2 = new Weather(recordWithId.getId(), 15.5, false, true, 6, WeatherState.CLOUDY);

    public final UUID id = UUID.fromString("59c47568-fde0-4dd7-9aef-03db6a962810");

    @BeforeEach
    public void setup() {
        mockWeatherService = Mockito.mock(WeatherService.class);
        weatherController = new WeatherController(mockWeatherService);
    }

    @Test
    public void createWeather_shouldReturnWeatherAndCREATEDHttpStatus() {
        Mockito.when(mockWeatherService.create(Mockito.any())).thenReturn(recordWithId);
        ResponseEntity<Weather> response = weatherController.createWeather(input);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(recordWithId, response.getBody());
    }

    @Test
    public void getAllWeather_shouldReturnListOfWeatherAndOKHttpStatus() {
        List<Weather> weatherList = new ArrayList<>();
        weatherList.add(input);
        weatherList.add(input2);
        Mockito.when(mockWeatherService.getAll()).thenReturn(weatherList);
        ResponseEntity<List<Weather>> response = weatherController.getAllWeather();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(weatherList, response.getBody());
    }

    @Test
    public void getWeatherById_shouldReturnWeatherAndOKHttpStatus() {
        Mockito.when(mockWeatherService.getById(recordWithId.getId())).thenReturn(recordWithId);
        ResponseEntity<Weather> response = weatherController.getWeatherById(recordWithId.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(recordWithId, response.getBody());
    }

    @Test
    public void getWeatherById_shouldReturn404WhenWeatherNotFound() {
        Mockito.when(mockWeatherService.getById(id)).thenThrow(new WeatherNotFoundException("A weather with id: " + id + " was not found."));
        ResponseEntity<Weather> response = weatherController.getWeatherById(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getWeatherByTemp_shouldReturnListOfWeatherAndOKHttpStatus() {
        Mockito.when(mockWeatherService.getByTemp(recordWithId.getTemp())).thenReturn(List.of(recordWithId));
        ResponseEntity<List<Weather>> response = weatherController.getWeatherByTemp(recordWithId.getTemp());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(recordWithId), response.getBody());
    }

    @Test
    public void updateWeather_shouldReturnWeatherAndOKHttpStatus() {
        Mockito.when(mockWeatherService.update(input2, recordWithId.getId())).thenReturn(recordWithId2);
        ResponseEntity<Weather> response = weatherController.updateWeather(input2, recordWithId.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(recordWithId2, response.getBody());
    }

    @Test
    public void updateWeather_shouldReturn404WhenWeatherNotFound() {
        Mockito.when(mockWeatherService.update(input, id)).thenThrow(new WeatherNotFoundException("A weather with id: " + id + " was not found."));
        ResponseEntity<Weather> response = weatherController.updateWeather(input, id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void patchWeather_shouldReturnWeatherAndOKHttpStatus() {
        Mockito.when(mockWeatherService.patch(input2, recordWithId.getId())).thenReturn(recordWithId2);
        ResponseEntity<Weather> response = weatherController.patchWeather(input2, recordWithId.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(recordWithId2, response.getBody());
    }

    @Test
    public void patchWeather_shouldReturn404WhenWeatherNotFound() {
        Mockito.when(mockWeatherService.patch(input, id)).thenThrow(new WeatherNotFoundException("A weather with id: " + id + " was not found."));
        ResponseEntity<Weather> response = weatherController.patchWeather(input, id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteWeather_shouldReturnOKHttpStatus() {
        ResponseEntity<Weather> response = weatherController.deleteWeather(recordWithId.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}