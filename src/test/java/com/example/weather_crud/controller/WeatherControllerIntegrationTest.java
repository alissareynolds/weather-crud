package com.example.weather_crud.controller;

import com.example.weather_crud.models.Weather;
import com.example.weather_crud.models.WeatherState;
import com.example.weather_crud.service.WeatherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WeatherControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private WeatherService mockWeatherService;

    private final Weather weather = new Weather(UUID.fromString("59c47568-fde0-4dd7-9aef-03db6a962810"), 72.5, true, false, 12, WeatherState.PARTLY_CLOUDY);

    @Test
    public void createWeather() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/api/weather")
                .content(asJsonString(new Weather(null, 72.5, true, false, 12, WeatherState.PARTLY_CLOUDY)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getAllWeather() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/weather")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void getWeatherById() throws Exception {
        Mockito.when(mockWeatherService.getById(UUID.fromString("59c47568-fde0-4dd7-9aef-03db6a962810"))).thenReturn(new Weather());
        mvc.perform(MockMvcRequestBuilders
                .get("/api/weather/59c47568-fde0-4dd7-9aef-03db6a962810")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void getWeatherByTemp() throws Exception {
        Mockito.when(mockWeatherService.getByTemp(70.2)).thenReturn(List.of(new Weather()));
        mvc.perform(MockMvcRequestBuilders
                .get("/api/weather/temp/70.2")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void updateWeather() throws Exception {
        Mockito.when(mockWeatherService.getById(UUID.fromString("59c47568-fde0-4dd7-9aef-03db6a962810"))).thenReturn(weather);
        mvc.perform(MockMvcRequestBuilders
                .put("/api/weather/59c47568-fde0-4dd7-9aef-03db6a962810")
                .content(asJsonString(weather))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void patchWeather() throws Exception {
        Mockito.when(mockWeatherService.getById(UUID.fromString("59c47568-fde0-4dd7-9aef-03db6a962810"))).thenReturn(weather);
        mvc.perform(MockMvcRequestBuilders
                .patch("/api/weather/59c47568-fde0-4dd7-9aef-03db6a962810")
                .content(asJsonString(weather))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void deleteWeather() throws Exception {
        Mockito.when(mockWeatherService.getById(UUID.fromString("59c47568-fde0-4dd7-9aef-03db6a962810"))).thenReturn(weather);
        mvc.perform(MockMvcRequestBuilders
                .delete("/api/weather/59c47568-fde0-4dd7-9aef-03db6a962810")
                .content(asJsonString(weather))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
}
