package com.taxah.weathersenderproject.service;

import com.taxah.weathersenderproject.model.WeatherCurrent;
import com.taxah.weathersenderproject.model.decorator.StandardWeatherDataDecorator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

@Component
public class BotService extends TelegramLongPollingBot {

    private String botToken;
    private final String botName;
    private final Map<String, Long> subscribers;
    private final WeatherService weatherService;


    public BotService(@Value("${bot.token}") String botToken,
                      @Value("${bot.name}") String botName,
                      WeatherService weatherService) {
        super(botToken);
        this.botName = botName;
        this.subscribers = new HashMap<>();
        System.out.println(subscribers);
        this.weatherService = weatherService;
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

    @Scheduled(cron = "0/15 * * * * *")
    public void sendDailyMessages() {
        for (Long chatId : subscribers.values()) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(StandardWeatherDataDecorator.decorate(weatherService.getWeather()));
            try {
                this.execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace(System.out);
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
