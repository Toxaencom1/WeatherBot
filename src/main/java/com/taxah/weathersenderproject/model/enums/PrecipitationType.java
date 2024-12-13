package com.taxah.weathersenderproject.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum PrecipitationType {
    NONE("нет осадков"),
    RAIN("дождь"),
    SNOW("снег"),
    RAIN_SNOW("дождь со снегом"),
    ICE_PELLETS("ледяной дождь"),
    FROZEN_RAIN("замерзший дождь");

    private final String russianTranslation;

    private static final Map<String, PrecipitationType> BY_NAME = new HashMap<>();

    static {
        for (PrecipitationType type : values()) {
            BY_NAME.put(type.name().toLowerCase(), type);
        }
    }

    PrecipitationType(String russianTranslation) {
        this.russianTranslation = russianTranslation;
    }

    /**
     * Получение перевода на русский язык.
     */
    public String getRussianTranslation() {
        return russianTranslation;
    }

    /**
     * Возвращаем строковое представление для сериализации.
     */
    @JsonValue
    @Override
    public String toString() {
        return name().toLowerCase().replace("_", " ");
    }

    /**
     * Создаем `enum` из строки для десериализации.
     */
    @JsonCreator
    public static PrecipitationType fromString(String name) {
        System.out.println("Input name: " + name);
        PrecipitationType type = BY_NAME.getOrDefault(name.toLowerCase().replace("_", " "), NONE);
        System.out.println("Resolved type: " + type);
        return type;
    }
}
