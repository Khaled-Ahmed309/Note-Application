package com.example.notes.controller;

import com.example.notes.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService){
        this.weatherService=weatherService;
    }
    @GetMapping("/{city}")
    public ResponseEntity<?> getWeatherByCity(@PathVariable String city) {

        return weatherService.getWeather(city);
    }

}
