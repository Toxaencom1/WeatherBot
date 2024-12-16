package com.taxah.weathersenderproject.bot;

import com.taxah.weathersenderproject.model.weatherEntity.WeatherResponseData;
import com.taxah.weathersenderproject.service.WeatherBotService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class WeatherTelegramBot extends TelegramLongPollingBot {

    private static final String START_MESSAGE = """
            Приветствую Семья и Родственники
            Я написал полезное приложение по получению погоды
            из доступных источников, в случае вашей подписки
            на этот телеграм бот будет отправляться сообщение
            и данные о погоде на сутки.
            Для просмотра всех команд введите - "/help"
            Для подписки на бота - "/subscribe",
            а для отмены подписки на бота - "/unsubscribe".
            """;

    private static final String HELP_MESSAGE = """
            Команды для бота:
            /start - для приветственного сообщения
            /subscribe - для подписки на бота
            /unsubscribe - для отмены подписки на бота
            """;

    private final String botName;
    @Getter
    private final WeatherBotService service;
    @Getter
    @Setter
    private WeatherResponseData weather;


    public WeatherTelegramBot(@Value("${bot.token}") String botToken,
                              @Value("${bot.name}") String botName,
                              WeatherBotService service) {
        super(botToken);
        this.botName = botName;
        this.service = service;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            String firstName = update.getMessage().getFrom().getUserName();
            if (messageText.equalsIgnoreCase("/start")) {
                sendTextMessage(chatId,START_MESSAGE);
            }else if (messageText.equalsIgnoreCase("/subscribe")) {
                service.addSubscriber(firstName, chatId);
                sendTextMessage(chatId, "Вы подписаны на ежедневные уведомления!");
                System.out.println("Новый подписчик: " + firstName + " " + chatId);
            } else if (messageText.equalsIgnoreCase("/unsubscribe")) {
                service.removeSubscriber(chatId);
                sendTextMessage(chatId, "Вы успешно отписались от уведомлений!");
                System.out.println("Подписчик: " + firstName + " " + chatId + " отписался");
            } else if (messageText.equalsIgnoreCase("/help")) {
                sendTextMessage(chatId, HELP_MESSAGE);
            } else {
                sendTextMessage(chatId, "Такой команды нет. Введите \"/help\" для просмотра доступных команд");
            }
        }
    }

    public void sendTextMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace(System.out);
        }
    }

    public void sendTextMessage(long chatId, String text, InputFile inputFile) {
        if (inputFile != null) {
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(chatId);
            sendPhoto.setPhoto(inputFile);
            try {
                sendTextMessage(chatId, text);
                execute(sendPhoto);
            } catch (TelegramApiException e) {
                e.printStackTrace(System.out);
            }
        } else {
            sendTextMessage(chatId, "Ошибка получения погоды и файла");
        }
    }

    @Override
    public String getBotUsername() {
        return this.botName;
    }
}
