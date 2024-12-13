package com.taxah.weathersenderproject.service;

import com.taxah.weathersenderproject.model.WeatherResponseData;
import com.taxah.weathersenderproject.model.decorator.WeatherDecorator;
import com.taxah.weathersenderproject.repository.WeatherResponseDataRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class BotService extends TelegramLongPollingBot {

    private final String botName;
    private final Map<String, Long> subscribers;
    @Getter
    private final WeatherService weatherService;
    private WeatherDecorator weatherDecorator;
    @Getter
    private final WeatherResponseDataRepository repository;
    @Getter
    @Setter
    private WeatherResponseData weather;


    public BotService(@Value("${bot.token}") String botToken,
                      @Value("${bot.name}") String botName,
                      WeatherService weatherService,
                      WeatherDecorator weatherDecorator,
                      WeatherResponseDataRepository repository) {
        super(botToken);
        this.botName = botName;
        this.repository = repository;
        this.subscribers = new HashMap<>();
        this.weatherService = weatherService;
        this.weatherDecorator = weatherDecorator;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (messageText.equalsIgnoreCase("/start")) {
                String firstName = update.getMessage().getFrom().getUserName();
                addSubscriber(firstName, chatId);
                sendTextMessage(chatId, "Вы подписаны на ежедневные уведомления!");
                System.out.println("Новый подписчик: " + firstName + " " + chatId);
            }
        }
    }

    @Scheduled(cron = "0/7 * * * * *")
    public void sendDailyMessages() {
        if (!weather.getCreatedDay().equals(LocalDate.now())) {
            WeatherResponseData weatherUpdated = weatherService.getWeather();
            if (weatherUpdated != null) {
                System.out.println("Получаю новую погоду!!!");
                this.weather = repository.save(weatherUpdated);
            } else {
                System.out.println("Ошибка получения погоды");
            }
        }
        if (!subscribers.isEmpty()) {
            for (Long chatId : subscribers.values()) {
                SendMessage message = new SendMessage();
                message.setChatId(chatId);
                message.setText(weatherDecorator.decorate(weather));
                try {
                    this.execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace(System.out);
                }
            }
        }
    }

    public void addSubscriber(String name, Long chatId) {
        subscribers.put(name, chatId);
    }

    private void sendTextMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public String getBotUsername() {
        return this.botName;
    }
}
