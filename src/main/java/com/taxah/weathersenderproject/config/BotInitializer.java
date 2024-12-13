package com.taxah.weathersenderproject.config;

import com.taxah.weathersenderproject.model.WeatherResponseData;
import com.taxah.weathersenderproject.repository.WeatherResponseDataRepository;
import com.taxah.weathersenderproject.service.BotService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Data
public class BotInitializer {

    private final BotService bot;

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

        WeatherResponseDataRepository repository = bot.getRepository();
        WeatherResponseData byTodayWeather = repository.findByCreatedDay(LocalDate.now());

        if (byTodayWeather != null) {
            bot.setWeather(byTodayWeather);
        } else {
            WeatherResponseData weather = bot.getWeatherService().getWeather();
            weather.setCreatedDay(LocalDate.now());
            bot.setWeather(weather);
            repository.save(weather);
        }
        telegramBotsApi.registerBot(bot);
    }
}
