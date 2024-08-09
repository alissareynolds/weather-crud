package com.example.weather_crud.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "weather")
@NoArgsConstructor
@AllArgsConstructor
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Double temp;

    private Boolean isFahrenheit;

    private Boolean isCelsius;

    private Integer windSpeed;

    @Enumerated(EnumType.STRING)
    private WeatherState state;

    public Weather(Double temp, Boolean isFahrenheit, Boolean isCelsius, Integer windSpeed, WeatherState state) {
        this.temp = temp;
        this.isFahrenheit = isFahrenheit;
        this.isCelsius = isCelsius;
        this.windSpeed = windSpeed;
        this.state = state;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Boolean getIsFahrenheit() {
        return isFahrenheit;
    }

    public void setIsFahrenheit(Boolean isFahrenheit) {
        this.isFahrenheit = isFahrenheit;
    }

    public Boolean getIsCelsius() {
        return isCelsius;
    }

    public void setIsCelsius(Boolean isCelsius) {
        this.isCelsius = isCelsius;
    }

    public Integer getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Integer windSpeed) {
        this.windSpeed = windSpeed;
    }

    public WeatherState getState() {
        return state;
    }

    public void setState(WeatherState state) {
        this.state = state;
    }

}
