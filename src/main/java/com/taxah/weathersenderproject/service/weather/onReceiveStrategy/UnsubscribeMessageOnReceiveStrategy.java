package com.taxah.weathersenderproject.service.weather.onReceiveStrategy;

import com.taxah.weathersenderproject.bot.WeatherTelegramBot;
import com.taxah.weathersenderproject.constants.LogConstants;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;

import static com.taxah.weathersenderproject.constants.BotConstants.ALREADY_UNSUBSCRIBED_FROM_NOTIFICATIONS;
import static com.taxah.weathersenderproject.constants.BotConstants.SUCCESSFULLY_UNSUBSCRIBED_FROM_NOTIFICATIONS;
import static com.taxah.weathersenderproject.enums.Commands.UNSUBSCRIBE;

@Component
public class UnsubscribeMessageOnReceiveStrategy implements OnReceiveStrategy {
    @Override
    public boolean provision(Update update) {
        return update.hasMessage() &&
                update.getMessage().hasText() &&
                update.getMessage().getText().equalsIgnoreCase(UNSUBSCRIBE.getCommandName());
    }

    @Override
    public void apply(Update update, WeatherTelegramBot bot) {
        String firstName = update.getMessage().getFrom().getUserName();
        long chatId = update.getMessage().getChatId();
        boolean subscriberExist = bot.getBotFacade().removeSubscriber(chatId);
        if (subscriberExist) {
            bot.sendTextMessage(chatId, SUCCESSFULLY_UNSUBSCRIBED_FROM_NOTIFICATIONS);
            System.out.println(LogConstants.unsubscribe(firstName, chatId, LocalDateTime.now()));
        } else {
            bot.sendTextMessage(chatId, ALREADY_UNSUBSCRIBED_FROM_NOTIFICATIONS);
            System.out.println(LogConstants.unsubscribe(firstName, chatId, LocalDateTime.now()));
        }
    }
}
