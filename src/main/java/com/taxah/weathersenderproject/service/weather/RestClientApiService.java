package com.taxah.weathersenderproject.service.weather;

import com.taxah.weathersenderproject.model.weatherEntity.WeatherEntry;
import com.taxah.weathersenderproject.model.weatherEntity.WeatherResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Primary
@Service
@RequiredArgsConstructor
public class RestClientApiService implements WeatherApiService {
    @Value("${weatherAPI.url}")
    private String url;
    @Value("${weatherAPI.apiKey}")
    private String apiKey;

    private final RestClient restClient;


    @Override
    public WeatherEntry getWeather(String city) {
        String uriString = UriComponentsBuilder.fromUri(URI.create(url))
                .queryParam("place_id", city)
                .queryParam("sections", "current,hourly")
                .queryParam("language", "en")
                .queryParam("units", "auto")
                .toUriString();
        WeatherResponseData body = restClient.get()
                .uri(uriString)
                .accept(MediaType.APPLICATION_JSON)
                .header("X-API-Key", apiKey)
                .retrieve()
                .body(WeatherResponseData.class);

        return WeatherEntry.builder()
                .cityName(city)
                .weatherResponseData(body)
                .build();
    }
}
