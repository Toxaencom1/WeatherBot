package com.taxah.weathersenderproject.service.onReceiveStrategy;

import com.taxah.weathersenderproject.bot.WeatherTelegramBot;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.taxah.weathersenderproject.constants.BotConstants.ADMIN_CHAT_ID;
import static com.taxah.weathersenderproject.enums.Commands.SEND_TO_SUPPORT;

@Component
public class SendToSupportMessageOnReceiveStrategy implements OnReceiveStrategy {
    @Override
    public boolean provision(Update update) {
        return update.hasMessage() &&
                update.getMessage().hasText() &&
                update.getMessage().getText().startsWith(SEND_TO_SUPPORT.getCommandName());
    }

    @Override
    public void apply(Update update, WeatherTelegramBot bot) {
        String messageText = update.getMessage().getText().substring(SEND_TO_SUPPORT.getCommandName().length());
        String from = update.getMessage().getFrom().getUserName() + ": " + update.getMessage().getChatId().toString() + "\n";
        bot.sendTextMessage(ADMIN_CHAT_ID, from + messageText.trim());
    }
}
