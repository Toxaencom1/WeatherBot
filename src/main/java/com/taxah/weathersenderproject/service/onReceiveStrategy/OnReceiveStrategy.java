package com.taxah.weathersenderproject.service.onReceiveStrategy;

import com.taxah.weathersenderproject.bot.WeatherTelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface OnReceiveStrategy {
    boolean provision(Update update);

    void apply(Update update, WeatherTelegramBot bot);
}
