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
    @Value("${base.url}")
    private String url;
    @Value("${base.apiKey}")
    private String apiKey;

    private final RestTemplate template;

    public WeatherResponseData getWeather() {
        String uriString = UriComponentsBuilder.fromUri(URI.create(url))
                .queryParam("place_id","almaty")
                .queryParam("sections", "current,hourly")
                .queryParam("language", "en")
                .queryParam("units", "auto")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-Key", apiKey);
        headers.set("Accept", "application/*+json");

        // Упаковываем заголовки в HttpEntity
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Выполняем запрос и получаем объект
        ResponseEntity<WeatherResponseData> response = template.exchange(
                uriString,                       // URL запроса
                HttpMethod.GET,            // HTTP метод
                entity,                    // Заголовки запроса
                WeatherResponseData.class  // Класс ожидаемого ответа
        );
        return response.getBody();
    }
}
