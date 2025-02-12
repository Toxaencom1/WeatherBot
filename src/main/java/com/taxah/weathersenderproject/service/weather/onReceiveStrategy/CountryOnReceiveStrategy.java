package com.taxah.weathersenderproject.service.weather.onReceiveStrategy;

import com.taxah.weathersenderproject.bot.WeatherTelegramBot;
import com.taxah.weathersenderproject.constants.LogConstants;
import com.taxah.weathersenderproject.model.subscriberEntity.dto.SubscriberDTO;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.taxah.weathersenderproject.constants.BotConstants.COUNTRY_PREFIX;

@Component
public class CountryOnReceiveStrategy implements OnReceiveStrategy {
    @Override
    public boolean provision(Update update) {
        return update.hasCallbackQuery() && update.getCallbackQuery().getData().startsWith(COUNTRY_PREFIX);
    }

    @Override
    public void apply(Update update, WeatherTelegramBot bot) {
        String callbackData = update.getCallbackQuery().getData();
        long chatId = update.getCallbackQuery().getMessage().getChatId();

        SubscriberDTO subscriberDTO = bot.getSubscriberDtoFromForms(chatId);

        String countryName = callbackData.replace(COUNTRY_PREFIX, "");
        subscriberDTO.setCountry(countryName);
        bot.sendCitySelection(chatId, countryName);
        System.out.println(LogConstants.SELECT_CITY);
    }
}
