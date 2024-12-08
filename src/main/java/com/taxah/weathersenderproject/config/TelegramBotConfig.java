package com.taxah.weathersenderproject.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class TelegramBotConfig {
    @Value("${bot.token}")
    private String token;
    @Value("${bot.name}")
    private String name;
}
