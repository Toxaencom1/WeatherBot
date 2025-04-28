package com.taxah.weathersenderproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WeatherSenderProjectApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(WeatherSenderProjectApplication.class);
        ConfigurableEnvironment env = app.run(args).getEnvironment();

        System.out.println("===============================================");
        System.out.println("          Application version: " + env.getProperty("spring.application.version"));
        System.out.println("===============================================");
    }

}
