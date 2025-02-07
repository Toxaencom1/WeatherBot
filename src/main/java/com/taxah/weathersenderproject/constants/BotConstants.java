package com.taxah.weathersenderproject.constants;

public class BotConstants {
    public static final long ADMIN_CHAT_ID = 365980032;

    public static final String START_MESSAGE = """
            Приветствую Семья и Родственники
            Я написал полезное приложение по получению погоды
            из доступных источников, в случае вашей подписки
            на этот телеграм бот будет отправляться сообщение
            и данные о погоде на сутки.
            Для просмотра всех команд введите - "/help"
            Для подписки на бота - "/subscribe",
            а для отмены подписки на бота - "/unsubscribe".
            """;
    public static final String HELP_MESSAGE = """
            Команды для бота:
            /start - для приветственного сообщения
            /subscribe - для подписки на бота
            /unsubscribe - для отмены подписки на бота
            /send <сообщение> - отправляет сообщение администратору (сообщение - всё, что после пробела команды /send)
            """;
    public static final String ALREADY_SUBSCRIBED_TO_DAILY_NOTIFICATIONS = "Вы уже подписаны на ежедневные уведомления!";
    public static final String SUCCESSFULLY_UNSUBSCRIBED_FROM_NOTIFICATIONS = "Вы успешно отписались от уведомлений!";
    public static final String ALREADY_UNSUBSCRIBED_FROM_NOTIFICATIONS = "Вы не получаете уведомлений!";
    public static final String NO_SUCH_COMMAND = "Такой команды нет. Введите \"/help\" для просмотра доступных команд";
    public static final String COUNTRY_PREFIX = "COUNTRY_";
    public static final String CITY_PREFIX = "CITY_";
    public static final String SUCCESSFULLY_SUBSCRIBED_TO_DAILY_NOTIFICATIONS = "Вы успешно подписаны на ежедневные уведомления!";
    public static final String SELECT_CITY = "Выберите город: ";
    public static final String SELECT_COUNTRY = "Выберите страну:";
    public static final String ERROR_TEXT_MESSAGE = "Ошибка получения погоды и файла";
}
