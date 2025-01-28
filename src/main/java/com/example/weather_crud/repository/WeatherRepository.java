package com.example.weather_crud.repository;

import com.example.weather_crud.models.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WeatherRepository extends JpaRepository<Weather, UUID> {
    List<Weather> findByTemp(Double temp);
}
