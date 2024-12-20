package com.taxah.weathersenderproject.bot;

import com.taxah.weathersenderproject.model.weatherEntity.WeatherResponseData;
import com.taxah.weathersenderproject.service.WeatherBotFacade;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Data
public class WeatherBotInitializer {

    private final WeatherTelegramBot bot;

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

        WeatherBotFacade botFacade = bot.getBotFacade();
        if (bot.getBotFacade().isLocationsEmpty()) {
            bot.getBotFacade().fillLocations();
        }

        List<WeatherResponseData> byTodayWeather = botFacade.findWeatherByCreatedDay(LocalDate.now());

        botFacade.fetchSubscribers();

        if (byTodayWeather != null && !byTodayWeather.isEmpty()) {
            bot.setWeathers(byTodayWeather);
        } else {
            List<WeatherResponseData> weather = botFacade.getDailyWeather();
            bot.setWeathers(weather);
            botFacade.saveWeathers(weather);
        }
        telegramBotsApi.registerBot(bot);
    }
}
