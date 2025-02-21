package com.taxah.weathersenderproject.constants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogConstants {
    public static final String INITIALIZING_USER_REGISTRATION = "\nИнициализация регистрации пользователя";
    public static final String COUNTRY_SELECT = "Выбор страны...";
    public static final String SELECT_CITY = "Выбор города...";
    public static final String SUBSCRIBE = "Подписчик: %s %d подписался %s";
    public static final String UNSUBSCRIBE = "Подписчик: %s %d отписался %s";

    public static String subscribe(String name, Long chatId, LocalDateTime date) {
        return String.format(SUBSCRIBE, name, chatId, dateFormating(date));
    }

    public static String unsubscribe(String name, Long chatId, LocalDateTime date) {
        return String.format(UNSUBSCRIBE, name, chatId, dateFormating(date));
    }

    private static String dateFormating(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return date.format(formatter);
    }
}
