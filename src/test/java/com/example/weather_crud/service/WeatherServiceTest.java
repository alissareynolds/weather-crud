package com.example.weather_crud.service;

import com.example.weather_crud.exception.WeatherNotFoundException;
import com.example.weather_crud.models.Weather;
import com.example.weather_crud.models.WeatherState;
import com.example.weather_crud.repository.WeatherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WeatherServiceTest {

    private WeatherService weatherService;
    private WeatherRepository mockWeatherRepository;

    public final Weather input = new Weather(null, 72.5, true, false, 12, WeatherState.PARTLY_CLOUDY);
    public final Weather input2 = new Weather(null, 15.5, false, true, 6, WeatherState.CLOUDY);
    public final Weather recordWithId = new Weather(UUID.randomUUID(), 72.5, true, false, 12, WeatherState.PARTLY_CLOUDY);

    public final UUID id = UUID.fromString("59c47568-fde0-4dd7-9aef-03db6a962810");

    @BeforeEach
    public void setup() {
        mockWeatherRepository = Mockito.mock(WeatherRepository.class);
        weatherService = new WeatherService(mockWeatherRepository);
    }

    @Test
    public void create_shouldReturnCreatedWeather() {
        Mockito.when(mockWeatherRepository.save(Mockito.any())).thenReturn(recordWithId);
        Weather response = weatherService.create(input);
        assertEquals(recordWithId, response);
    }

    @Test
    public void getAll_shouldReturnListOfWeather() {
        List<Weather> weatherList = new ArrayList<>();
        weatherList.add(input);
        weatherList.add(input2);
        Mockito.when(mockWeatherRepository.findAll()).thenReturn(weatherList);
        List<Weather> response = weatherService.getAll();
        assertEquals(weatherList, response);
    }

    @Test
    public void getById_shouldReturnWeather() {
        Mockito.when(mockWeatherRepository.findById(recordWithId.getId())).thenReturn(Optional.of(recordWithId));
        Weather response = weatherService.getById(recordWithId.getId());
        assertEquals(recordWithId, response);
    }

    @Test
    public void getById_throwsExceptionWhenWeatherWasNotFound() {
        Mockito.when(mockWeatherRepository.findById(id)).thenReturn(Optional.empty());
        WeatherNotFoundException exception = assertThrows(WeatherNotFoundException.class, () -> weatherService.getById(id));
        assertEquals("A weather with id: " + id + " was not found.", exception.getLocalizedMessage());
    }

    @Test
    public void getByTemp_shouldReturnListOfWeather() {
        Mockito.when(mockWeatherRepository.findByTemp(recordWithId.getTemp())).thenReturn(List.of(recordWithId));
        List<Weather> response = weatherService.getByTemp(recordWithId.getTemp());
        assertEquals(List.of(recordWithId), response);
    }

    @Test
    public void update_shouldReturnUpdatedWeather() {
        Mockito.when(mockWeatherRepository.findById(recordWithId.getId())).thenReturn(Optional.of(recordWithId));
        Mockito.when(mockWeatherRepository.save(Mockito.any())).thenReturn(recordWithId);
        Weather response = weatherService.update(input2, recordWithId.getId());
        assertEquals(recordWithId, response);
    }

    @Test
    public void update_throwsExceptionWhenWeatherWasNotFound() {
        Mockito.when(mockWeatherRepository.findById(id)).thenReturn(Optional.empty());
        WeatherNotFoundException exception = assertThrows(WeatherNotFoundException.class, () -> weatherService.update(input, id));
        assertEquals("A weather with id: " + id + " was not found.", exception.getMessage());
    }

    @Test
    public void patch_throwsExceptionWhenWeatherWasNotFound() {
        Mockito.when(mockWeatherRepository.findById(id)).thenReturn(Optional.empty());
        WeatherNotFoundException exception = assertThrows(WeatherNotFoundException.class, () -> weatherService.patch(input, id));
        assertEquals("A weather with id: " + id + " was not found.", exception.getMessage());
    }

    @Test
    public void patch_shouldReturnUpdatedTemp() {
        Weather input = new Weather();
        input.setTemp(70.2);
        Mockito.when(mockWeatherRepository.findById(recordWithId.getId())).thenReturn(Optional.of(recordWithId));
        Mockito.when(mockWeatherRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
        Weather response = weatherService.patch(input, recordWithId.getId());
        assertEquals(recordWithId.getId(), response.getId());
        assertEquals(70.2, response.getTemp());
        assertEquals(true, response.getIsFahrenheit());
        assertEquals(false, response.getIsCelsius());
        assertEquals(12, response.getWindSpeed());
        assertEquals(WeatherState.PARTLY_CLOUDY, response.getState());
    }

    @Test
    public void patch_shouldReturnUpdatedIsFahrenheit() {
        Weather input = new Weather();
        input.setIsFahrenheit(false);
        Mockito.when(mockWeatherRepository.findById(recordWithId.getId())).thenReturn(Optional.of(recordWithId));
        Mockito.when(mockWeatherRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
        Weather response = weatherService.patch(input, recordWithId.getId());
        assertEquals(recordWithId.getId(), response.getId());
        assertEquals(72.5, response.getTemp());
        assertEquals(false, response.getIsFahrenheit());
        assertEquals(false, response.getIsCelsius());
        assertEquals(12, response.getWindSpeed());
        assertEquals(WeatherState.PARTLY_CLOUDY, response.getState());
    }

    @Test
    public void patch_shouldReturnUpdatedIsCelsius() {
        Weather input = new Weather();
        input.setIsCelsius(true);
        Mockito.when(mockWeatherRepository.findById(recordWithId.getId())).thenReturn(Optional.of(recordWithId));
        Mockito.when(mockWeatherRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
        Weather response = weatherService.patch(input, recordWithId.getId());
        assertEquals(recordWithId.getId(), response.getId());
        assertEquals(72.5, response.getTemp());
        assertEquals(true, response.getIsFahrenheit());
        assertEquals(true, response.getIsCelsius());
        assertEquals(12, response.getWindSpeed());
        assertEquals(WeatherState.PARTLY_CLOUDY, response.getState());
    }

    @Test
    public void patch_shouldReturnUpdatedWindSpeed() {
        Weather input = new Weather();
        input.setWindSpeed(10);
        Mockito.when(mockWeatherRepository.findById(recordWithId.getId())).thenReturn(Optional.of(recordWithId));
        Mockito.when(mockWeatherRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
        Weather response = weatherService.patch(input, recordWithId.getId());
        assertEquals(recordWithId.getId(), response.getId());
        assertEquals(72.5, response.getTemp());
        assertEquals(true, response.getIsFahrenheit());
        assertEquals(false, response.getIsCelsius());
        assertEquals(10, response.getWindSpeed());
        assertEquals(WeatherState.PARTLY_CLOUDY, response.getState());
    }

    @Test
    public void patch_shouldReturnUpdatedState() {
        Weather input = new Weather();
        input.setState(WeatherState.SNOW);
        Mockito.when(mockWeatherRepository.findById(recordWithId.getId())).thenReturn(Optional.of(recordWithId));
        Mockito.when(mockWeatherRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
        Weather response = weatherService.patch(input, recordWithId.getId());
        assertEquals(recordWithId.getId(), response.getId());
        assertEquals(72.5, response.getTemp());
        assertEquals(true, response.getIsFahrenheit());
        assertEquals(false, response.getIsCelsius());
        assertEquals(12, response.getWindSpeed());
        assertEquals(WeatherState.SNOW, response.getState());
    }


    @Test
    public void delete_callsRepositoryDeleteMethod() {
        weatherService.delete(id);
        Mockito.verify(mockWeatherRepository).deleteById(id);
    }
}