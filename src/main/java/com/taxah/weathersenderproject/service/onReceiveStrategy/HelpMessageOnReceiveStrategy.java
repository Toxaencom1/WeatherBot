package com.taxah.weathersenderproject.service.onReceiveStrategy;

import com.taxah.weathersenderproject.bot.WeatherTelegramBot;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.taxah.weathersenderproject.constants.BotConstants.HELP_MESSAGE;
import static com.taxah.weathersenderproject.enums.Commands.HELP;

@Component
public class HelpMessageOnReceiveStrategy implements OnReceiveStrategy {
    @Override
    public boolean provision(Update update) {
        return update.hasMessage() &&
                update.getMessage().hasText() &&
                update.getMessage().getText().equalsIgnoreCase(HELP.getCommandName());
    }

    @Override
    public void apply(Update update, WeatherTelegramBot bot) {
        long chatId = update.getMessage().getChatId();
        bot.sendTextMessage(chatId, HELP_MESSAGE);
    }
}
