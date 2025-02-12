package com.taxah.weathersenderproject.service.weather.onReceiveStrategy;

import com.taxah.weathersenderproject.bot.WeatherTelegramBot;
import com.taxah.weathersenderproject.enums.Commands;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.taxah.weathersenderproject.constants.BotConstants.NO_SUCH_COMMAND;

@Component
public class OtherMessageOnReceiveStrategy implements OnReceiveStrategy {
    @Override
    public boolean provision(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            Pattern pattern = Pattern.compile("^(?<command>/\\w+)\\b");
            Matcher matcher = pattern.matcher(message);
            if (matcher.find()) {
                String command = matcher.group("command");
                return !Commands.getStringCommandsList().contains(command);
            }
        }
        return false;
    }

    @Override
    public void apply(Update update, WeatherTelegramBot bot) {
        long chatId = update.getMessage().getChatId();
        bot.sendTextMessage(chatId, NO_SUCH_COMMAND);
    }
}
