package com.taxah.weathersenderproject.service;

import com.taxah.weathersenderproject.bot.WeatherTelegramBot;
import com.taxah.weathersenderproject.model.subscriberEntity.Subscriber;
import com.taxah.weathersenderproject.model.weatherEntity.City;
import com.taxah.weathersenderproject.model.weatherEntity.WeatherResponseData;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@RequiredArgsConstructor
@Service
public class SubscribersNotificationService {
    private final WeatherTelegramBot bot;

    @Scheduled(cron = "${bot.cron}")
    public void sendDailyMessages() {
        WeatherBotFacade botFacade = bot.getBotFacade();
        List<WeatherResponseData> weathers = bot.getWeathers();

        if (weathers != null && !weathers.isEmpty()) {
            for (Subscriber subscriber : bot.getSubscribers()) {
                City city = subscriber.getLocation().getCity();
                WeatherResponseData weather = weathers.stream()
                        .filter(x -> x.getTimezone().contains(city.getName())).findFirst().orElse(null);
                String message = botFacade.decorateText(weather, city.getName());
                bot.sendTextMessage(subscriber.getChatId(), message, botFacade.decoratePhoto(weather));
            }
        }
    }
}
