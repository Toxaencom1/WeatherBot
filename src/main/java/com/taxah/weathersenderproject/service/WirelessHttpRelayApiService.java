package com.taxah.weathersenderproject.service;

import com.taxah.weathersenderproject.model.relay.Time;
import com.taxah.weathersenderproject.model.relay.TimerResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WirelessHttpRelayApiService {
    private final RestClient restClient;
    @Value("${relay.ip}")
    private String ip;

    @SneakyThrows
    public ResponseEntity<String> relayOffByTimer(int delaySeconds) {

        String timeUrl = String.format("http://%s/cm?cmnd=Time", ip);
        ResponseEntity<Time> timeResp = restClient.get().uri(timeUrl).retrieve().toEntity(Time.class);
        String timeStr = Objects.requireNonNull(timeResp.getBody()).getTime();

        LocalTime now = LocalTime.parse(timeStr.substring(11));
        LocalTime triggerTime = now.plusSeconds(delaySeconds);
        String timeFormatted = triggerTime.format(DateTimeFormatter.ofPattern("HH:mm"));

        String json = String.format("{\"Enable\":1,\"Mode\":0,\"Time\":\"%s\",\"Days\":\"1111111\",\"Repeat\":0,\"Action\":0}", timeFormatted);
        String encoded = URLEncoder.encode(json, StandardCharsets.UTF_8);
        String url = String.format("http://%s/cm?cmnd=Timer2%%20%s", ip, encoded);

        URI uri = new URI(url);
        System.out.println(uri);
        ResponseEntity<TimerResponse> responseEntity = restClient.get()
                .uri(uri)
                .header("Content-Type", "application/json")
                .retrieve()
                .toEntity(TimerResponse.class);

        System.out.println("Tasmota ответ: " + responseEntity.getBody());
        return ResponseEntity.status(HttpStatus.OK).body("Timer is set successfully");
    }
}
