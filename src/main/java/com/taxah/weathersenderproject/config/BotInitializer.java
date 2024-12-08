package com.taxah.weathersenderproject.config;

import com.taxah.weathersenderproject.controller.WeatherController;
import com.taxah.weathersenderproject.model.WeatherCurrent;
import com.taxah.weathersenderproject.model.WeatherResponseData;
import com.taxah.weathersenderproject.service.BotService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
@RequiredArgsConstructor
@Data
public class BotInitializer {

    private final BotService bot;


    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
    }
}
