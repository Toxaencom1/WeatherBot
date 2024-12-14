package com.taxah.weathersenderproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WeatherSenderProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherSenderProjectApplication.class, args);
    }

}
