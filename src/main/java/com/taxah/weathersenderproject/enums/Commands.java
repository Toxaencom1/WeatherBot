package com.taxah.weathersenderproject.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum Commands {
    START("/start"),
    HELP("/help"),
    SUBSCRIBE("/subscribe"),
    UNSUBSCRIBE("/unsubscribe"),
    SEND_TO_SUPPORT("/send");

    private final String commandName;

    Commands(String commandName) {
        this.commandName = commandName;
    }

    public static List<String> getStringCommandsList() {
        return Arrays.stream(Commands.values()).map(Commands::getCommandName).toList();
    }
}
