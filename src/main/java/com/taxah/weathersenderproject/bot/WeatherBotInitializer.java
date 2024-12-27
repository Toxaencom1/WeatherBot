package com.taxah.weathersenderproject.bot;

import com.taxah.weathersenderproject.model.weatherEntity.WeatherEntry;
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
        if (botFacade.isLocationsEmpty()) {
            botFacade.fillLocations();
        }

        LocalDate now = LocalDate.now();

        List<WeatherEntry> byTodayWeather = botFacade.findWeatherByCreatedDay(now);
        botFacade.fetchSubscribers();

        if (byTodayWeather != null && !byTodayWeather.isEmpty()) {
            bot.setWeathers(byTodayWeather);
        } else {
            List<WeatherEntry> weather = botFacade.getDailyWeather();
            bot.setWeathers(weather);
            botFacade.saveWeathers(weather);
            botFacade.deleteAllByDateBeforeOrEqual(now.minusDays(1));
        }
        telegramBotsApi.registerBot(bot);
    }
}
