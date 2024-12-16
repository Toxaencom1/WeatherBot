package com.taxah.weathersenderproject.service;

import com.taxah.weathersenderproject.bot.WeatherTelegramBot;
import com.taxah.weathersenderproject.model.subscriberEntity.Subscriber;
import com.taxah.weathersenderproject.model.weatherEntity.WeatherResponseData;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@Service
public class SubscribersNotificationService {
    private final WeatherTelegramBot bot;
    private final SubscriberService subscriberService;

    @Scheduled(cron = "${bot.cron}")
    public void sendDailyMessages() {
        WeatherBotService service = bot.getService();
        WeatherResponseData weather = bot.getWeather();

        String message = service.decorateText(weather);

        if (weather == null || !weather.getCreatedDay().equals(LocalDate.now())) {
            WeatherResponseData weatherUpdated = service.getWeather();
            if (weatherUpdated != null) {
                System.out.println("Получаю новую погоду!!!");
                WeatherResponseData receivedWeather = service.saveWeather(weatherUpdated);
                bot.setWeather(receivedWeather);
                message = service.decorateText(receivedWeather);
            } else {
                System.out.println("Ошибка получения погоды");
                message = "Ошибка получения погоды";
            }
        }
        if (!subscriberService.isEmpty()) {
            for (Subscriber subscriber : subscriberService) {
                bot.sendTextMessage(subscriber.getChatId(), message, service.decoratePhoto(weather));
            }
        }
    }
}
