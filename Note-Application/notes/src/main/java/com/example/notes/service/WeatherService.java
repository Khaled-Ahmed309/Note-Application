package com.example.notes.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class WeatherService {

    // the key is: 6384d2225e0ecc414863fdff1610a76d

    private final RestClient restClient;

    public WeatherService(RestClient.Builder builder){
        this.restClient=builder.baseUrl("https://api.openweathermap.org/data/2.5").build();
    }

    public ResponseEntity<?> getWeather(String city){
        String apiKey="6384d2225e0ecc414863fdff1610a76d";

       String response=restClient.get().uri(uriBuilder ->uriBuilder
                .path("/weather")
                .queryParam("q",city)
                .queryParam("appid",apiKey)
                .queryParam("units","metric")
                .build()).retrieve().body(String.class);
        return ResponseEntity.ok(response);
    }
}
