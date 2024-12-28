package com.taxah.weathersenderproject.bot;

import com.taxah.weathersenderproject.constants.BotConstants;
import com.taxah.weathersenderproject.constants.LogConstants;
import com.taxah.weathersenderproject.model.subscriberEntity.Subscriber;
import com.taxah.weathersenderproject.model.subscriberEntity.dto.SubscriberDTO;
import com.taxah.weathersenderproject.model.weatherEntity.City;
import com.taxah.weathersenderproject.model.weatherEntity.Country;
import com.taxah.weathersenderproject.model.weatherEntity.Location;
import com.taxah.weathersenderproject.model.weatherEntity.WeatherEntry;
import com.taxah.weathersenderproject.service.WeatherBotFacade;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WeatherTelegramBot extends TelegramLongPollingBot {

    private final String botName;
    @Getter
    private final WeatherBotFacade botFacade;
    @Getter
    @Setter
    private List<WeatherEntry> weathers;
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

            if (messageText.equalsIgnoreCase(BotConstants.COMMAND_START)) {
                sendTextMessage(chatId, BotConstants.START_MESSAGE);
            } else if (messageText.equalsIgnoreCase(BotConstants.COMMAND_SUBSCRIBE)) {
                if (botFacade.checkIfSubscriberExists(chatId)) {
                    sendTextMessage(chatId, BotConstants.ALREADY_SUBSCRIBED_TO_DAILY_NOTIFICATIONS);
                } else {
                    System.out.println(LogConstants.INITIALIZING_USER_REGISTRATION);
                    subscriberForms.put(chatId, SubscriberDTO.builder().firstName(firstName).build());
                    sendCountrySelection(chatId);
                    System.out.println(LogConstants.COUNTRY_SELECT);
                }
            } else if (messageText.equalsIgnoreCase(BotConstants.COMMAND_UNSUBSCRIBE)) {
                botFacade.removeSubscriber(chatId);
                sendTextMessage(chatId, BotConstants.SUCCESSFULLY_UNSUBSCRIBED_FROM_NOTIFICATIONS);
                System.out.println(LogConstants.unsubscribe(firstName, chatId, LocalDateTime.now()));
            } else if (messageText.equalsIgnoreCase(BotConstants.COMMAND_HELP)) {
                sendTextMessage(chatId, BotConstants.HELP_MESSAGE);
            } else {
                sendTextMessage(chatId, BotConstants.NO_SUCH_COMMAND);
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            SubscriberDTO subscriberDTO = subscriberForms.get(chatId);
            if (callbackData.startsWith(BotConstants.COUNTRY_PREFIX)) {
                String countryName = callbackData.replace(BotConstants.COUNTRY_PREFIX, "");
                subscriberDTO.setCountry(countryName);
                sendCitySelection(chatId, countryName);
                System.out.println(LogConstants.SELECT_CITY);
            } else if (callbackData.startsWith(BotConstants.CITY_PREFIX)) {
                String cityName = callbackData.replace(BotConstants.CITY_PREFIX, "");
                subscriberDTO.setCity(cityName);
                Location location = botFacade.getLocation(subscriberDTO.getCountry(), subscriberDTO.getCity());
                Subscriber subscriber = Subscriber.builder()
                        .name(subscriberDTO.getFirstName())
                        .chatId(chatId)
                        .location(location)
                        .build();
                botFacade.addSubscriber(subscriber);
                sendTextMessage(chatId, BotConstants.SUCCESSFULLY_SUBSCRIBED_TO_DAILY_NOTIFICATIONS);
                System.out.println(LogConstants.subscribe(subscriberDTO.getFirstName(), chatId, LocalDateTime.now()));
                subscriberForms.remove(chatId);
            }
        }
    }

    private void sendCitySelection(long chatId, String countryName) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(BotConstants.SELECT_CITY);

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttonsRows = new ArrayList<>();

        List<City> countries = botFacade.getAllCitiesBYCountryName(countryName);
        for (City city : countries) {
            List<InlineKeyboardButton> butonsList = new ArrayList<>();
            String cityName = city.getName();
            butonsList.add(InlineKeyboardButton.builder()
                    .text(cityName)
                    .callbackData(BotConstants.CITY_PREFIX + cityName)
                    .build());
            buttonsRows.add(butonsList);
        }

        keyboard.setKeyboard(buttonsRows);
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
        message.setText(BotConstants.SELECT_COUNTRY);

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttonsRows = new ArrayList<>();

        List<Country> countries = botFacade.getAllCountries();
        for (Country country : countries) {
            List<InlineKeyboardButton> butonsList = new ArrayList<>();
            String countryName = country.getName();
            butonsList.add(InlineKeyboardButton.builder()
                    .text(countryName)
                    .callbackData(BotConstants.COUNTRY_PREFIX + countryName)
                    .build());
            buttonsRows.add(butonsList);
        }

        keyboard.setKeyboard(buttonsRows);
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
            sendTextMessage(chatId, BotConstants.ERROR_TEXT_MESSAGE);
        }
    }

    @Scheduled(cron = "${weather.cron}")
    public void setDailyWeather() {
        this.weathers = botFacade.getDailyWeather();
        botFacade.deleteAllOtherWeathersByDate(LocalDate.now());
    }

    @Override
    public String getBotUsername() {
        return this.botName;
    }
}