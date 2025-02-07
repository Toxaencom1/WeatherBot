package com.taxah.weathersenderproject.service.onReceiveStrategy;

import com.taxah.weathersenderproject.bot.WeatherTelegramBot;
import com.taxah.weathersenderproject.constants.LogConstants;
import com.taxah.weathersenderproject.model.subscriberEntity.Subscriber;
import com.taxah.weathersenderproject.model.subscriberEntity.dto.SubscriberDTO;
import com.taxah.weathersenderproject.model.weatherEntity.Location;
import com.taxah.weathersenderproject.service.WeatherBotFacade;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;

import static com.taxah.weathersenderproject.constants.BotConstants.CITY_PREFIX;
import static com.taxah.weathersenderproject.constants.BotConstants.SUCCESSFULLY_SUBSCRIBED_TO_DAILY_NOTIFICATIONS;

@Component
public class CityOnReceiveStrategy implements OnReceiveStrategy {
    @Override
    public boolean provision(Update update) {
        return update.hasCallbackQuery() && update.getCallbackQuery().getData().startsWith(CITY_PREFIX);
    }

    @Override
    public void apply(Update update, WeatherTelegramBot bot) {
        String callbackData = update.getCallbackQuery().getData();
        long chatId = update.getCallbackQuery().getMessage().getChatId();

        SubscriberDTO subscriberDTO = bot.getSubscriberDtoFromForms(chatId);

        String cityName = callbackData.replace(CITY_PREFIX, "");
        subscriberDTO.setCity(cityName);
        WeatherBotFacade botFacade = bot.getBotFacade();
        Location location = botFacade.getLocation(subscriberDTO.getCountry(), subscriberDTO.getCity());
        Subscriber subscriber = Subscriber.builder()
                .name(subscriberDTO.getFirstName())
                .chatId(chatId)
                .location(location)
                .build();
        botFacade.addSubscriber(subscriber);
        bot.sendTextMessage(chatId, SUCCESSFULLY_SUBSCRIBED_TO_DAILY_NOTIFICATIONS);
        System.out.println(LogConstants.subscribe(subscriberDTO.getFirstName(), chatId, LocalDateTime.now()));
        bot.removeFromForms(chatId);
    }
}
