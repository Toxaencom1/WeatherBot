package com.taxah.weathersenderproject.service.onReceiveStrategy;

import com.taxah.weathersenderproject.bot.WeatherTelegramBot;
import com.taxah.weathersenderproject.constants.LogConstants;
import com.taxah.weathersenderproject.service.WeatherBotFacade;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.taxah.weathersenderproject.constants.BotConstants.ALREADY_SUBSCRIBED_TO_DAILY_NOTIFICATIONS;
import static com.taxah.weathersenderproject.enums.Commands.SUBSCRIBE;

@Component
public class SubscribeMessageOnReceiveStrategy implements OnReceiveStrategy {
    @Override
    public boolean provision(Update update) {
        return update.hasMessage() &&
                update.getMessage().hasText() &&
                update.getMessage().getText().equalsIgnoreCase(SUBSCRIBE.getCommandName());
    }

    @Override
    public void apply(Update update, WeatherTelegramBot bot) {
        WeatherBotFacade botFacade = bot.getBotFacade();
        String firstName = update.getMessage().getFrom().getUserName();
        long chatId = update.getMessage().getChatId();
        if (botFacade.checkIfSubscriberExists(chatId)) {
            bot.sendTextMessage(chatId, ALREADY_SUBSCRIBED_TO_DAILY_NOTIFICATIONS);
        } else {
            System.out.println(LogConstants.INITIALIZING_USER_REGISTRATION);
            bot.putToSubscriberForms(chatId, firstName);
            bot.sendCountrySelection(chatId);
            System.out.println(LogConstants.COUNTRY_SELECT);
        }
    }
}
