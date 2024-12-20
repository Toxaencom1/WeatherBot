package com.taxah.weathersenderproject.bot;

import com.taxah.weathersenderproject.model.subscriberEntity.Subscriber;
import com.taxah.weathersenderproject.model.weatherEntity.City;
import com.taxah.weathersenderproject.model.weatherEntity.Country;
import com.taxah.weathersenderproject.model.weatherEntity.Location;
import com.taxah.weathersenderproject.model.weatherEntity.WeatherResponseData;
import com.taxah.weathersenderproject.model.weatherEntity.dto.SubscriberDTO;
import com.taxah.weathersenderproject.service.WeatherBotFacade;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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
    private final WeatherBotFacade botFacade;
    @Getter
    @Setter
    private List<WeatherResponseData> weathers;
    @Getter
    private final ConcurrentHashMap<Long, SubscriberDTO> subscriberForms = new ConcurrentHashMap<>();


    public WeatherTelegramBot(@Value("${bot.token}") String botToken,
                              @Value("${bot.name}") String botName,
                              WeatherBotFacade botFacade) {
        super(botToken);
        this.botName = botName;
        this.botFacade = botFacade;
        this.weathers = new ArrayList<>();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String firstName = update.getMessage().getFrom().getUserName();
            long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();

            if (messageText.equalsIgnoreCase("/start")) {
                sendTextMessage(chatId, START_MESSAGE);
            } else if (messageText.equalsIgnoreCase("/subscribe")) {
                if (botFacade.checkIfSubscriberExists(chatId)) {
                    sendTextMessage(chatId, "Вы уже подписаны на ежедневные уведомления!");
                } else {
                    subscriberForms.put(chatId, SubscriberDTO.builder().firstName(firstName).build());
                    sendCountrySelection(chatId);
                }
            } else if (messageText.equalsIgnoreCase("/unsubscribe")) {
                botFacade.removeSubscriber(chatId);
                sendTextMessage(chatId, "Вы успешно отписались от уведомлений!");
                System.out.println("Подписчик: " + firstName + " " + chatId + " отписался");
            } else if (messageText.equalsIgnoreCase("/help")) {
                sendTextMessage(chatId, HELP_MESSAGE);
            } else {
                sendTextMessage(chatId, "Такой команды нет. Введите \"/help\" для просмотра доступных команд");
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            SubscriberDTO subscriberDTO = subscriberForms.get(chatId);
            if (callbackData.startsWith("COUNTRY_")) {
                String countryName = callbackData.replace("COUNTRY_", "");
                subscriberDTO.setCountry(countryName);
                sendCitySelection(chatId, countryName);

            } else if (callbackData.startsWith("CITY_")) {
                String cityName = callbackData.replace("CITY_", "");
                subscriberDTO.setCity(cityName);
                Location location = botFacade.getLocation(subscriberDTO.getCountry(), subscriberDTO.getCity());
                Subscriber subscriber = Subscriber.builder()
                        .name(subscriberDTO.getFirstName())
                        .chatId(chatId)
                        .location(location)
                        .build();
                botFacade.addSubscriber(subscriber);
                sendTextMessage(chatId, "Вы успешно подписаны на ежедневные уведомления!");
            }
        }
    }

    private void sendCitySelection(long chatId, String countryName) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите город: ");

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<City> countries = botFacade.getAllCitiesBYCountryName(countryName);
        for (City city : countries) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            String cityName = city.getName();
            row.add(InlineKeyboardButton.builder()
                    .text(cityName)
                    .callbackData("CITY_" + cityName)
                    .build());
            rows.add(row);
        }

        keyboard.setKeyboard(rows);
        message.setReplyMarkup(keyboard);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace(System.err);
        }
    }

    private void sendCountrySelection(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите страну:");

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<Country> countries = botFacade.getAllCountries();
        for (Country country : countries) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            String countryName = country.getName();
            row.add(InlineKeyboardButton.builder()
                    .text(countryName)
                    .callbackData("COUNTRY_" + countryName)
                    .build());
            rows.add(row);
        }

        keyboard.setKeyboard(rows);
        message.setReplyMarkup(keyboard);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace(System.err);
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

    public List<Subscriber> getSubscribers(){
        return botFacade.getSubscribers();
    }
}
