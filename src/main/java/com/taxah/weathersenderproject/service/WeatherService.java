package com.taxah.weathersenderproject.service;

import com.taxah.weathersenderproject.model.WeatherResponseData;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@Data
public class WeatherService {

    @Value("${weather.city}")
    private String city;
    @Value("${weatherAPI.url}")
    private String url;
    @Value("${weatherAPI.apiKey}")
    private String apiKey;

    private final RestTemplate template;

    public WeatherResponseData getWeather() {
        String uriString = UriComponentsBuilder.fromUri(URI.create(url))
                .queryParam("place_id",city)
                .queryParam("sections", "current,hourly")
                .queryParam("language", "en")
                .queryParam("units", "auto")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-Key", apiKey);
        headers.set("Accept", "application/*+json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<WeatherResponseData> response = template.exchange(
                uriString,                       // URL запроса
                HttpMethod.GET,            // HTTP метод
                entity,                    // Заголовки запроса
                WeatherResponseData.class  // Класс ожидаемого ответа
        );
        return response.getBody();
    }
}
