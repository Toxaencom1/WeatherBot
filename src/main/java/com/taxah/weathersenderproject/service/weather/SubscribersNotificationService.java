package com.taxah.weathersenderproject.service.weather;

import com.taxah.weathersenderproject.bot.WeatherTelegramBot;
import com.taxah.weathersenderproject.model.subscriberEntity.Subscriber;
import com.taxah.weathersenderproject.model.weatherEntity.City;
import com.taxah.weathersenderproject.model.weatherEntity.WeatherEntry;
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
    public boolean sendDailyMessages() {
        WeatherBotFacade botFacade = bot.getBotFacade();
        List<WeatherEntry> weathers = bot.getWeathers();
        List<Subscriber> subscribers = botFacade.getSubscribers();

        if (weathers != null && !weathers.isEmpty() && subscribers != null && !subscribers.isEmpty()) {
            for (Subscriber subscriber : subscribers) {
                City city = subscriber.getLocation().getCity();
                WeatherEntry weather = weathers.stream()
                        .filter(entry -> entry.getCityName().equals(city.getName()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("There is no weather for " + city.getName()));
                String message = botFacade.decorateText(weather);
                bot.sendTextMessage(subscriber.getChatId(), message, botFacade.decoratePhoto(weather.getWeatherResponseData()));
            }
            return true;
        } else {
            return false;
        }
    }

    public void sendOrdinaryMessage(String message) {
        WeatherBotFacade botFacade = bot.getBotFacade();
        List<Subscriber> subscribers = botFacade.getSubscribers();
        for (Subscriber subscriber : subscribers) {
            bot.sendTextMessage(subscriber.getChatId(), message);
        }
    }

    public void sendCommonMessage(Long chatId, String message) {
        bot.sendTextMessage(chatId, message);
    }
}
