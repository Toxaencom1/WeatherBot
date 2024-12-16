package com.taxah.weathersenderproject.bot;

import com.taxah.weathersenderproject.model.weatherEntity.WeatherResponseData;
import com.taxah.weathersenderproject.service.WeatherBotService;
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
public class WeatherBotInitializer {

    private final WeatherTelegramBot bot;

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

        WeatherBotService service = bot.getService();
        WeatherResponseData byTodayWeather = service.findByCreatedDay(LocalDate.now());

        service.fetchSubscribers();

        if (byTodayWeather != null) {
            bot.setWeather(byTodayWeather);
        } else {
            WeatherResponseData weather = service.getWeatherService().getWeather();
            weather.setCreatedDay(LocalDate.now());
            bot.setWeather(weather);
            service.saveWeather(weather);
        }
        telegramBotsApi.registerBot(bot);
    }
}
