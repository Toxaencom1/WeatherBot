package com.taxah.weathersenderproject.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum WeatherCondition {
    NOT_AVAILABLE("Недоступно"),
    SUNNY("Солнечно"),
    MOSTLY_SUNNY("Преимущественно солнечно"),
    PARTLY_SUNNY("Переменная облачность"),
    MOSTLY_CLOUDY("Преимущественно облачно"),
    CLOUDY("Облачно"),
    OVERCAST("Пасмурно"),
    OVERCAST_LOW_CLOUDS("Пасмурно с низкими облаками"),
    FOG("Туман"),
    LIGHT_RAIN("Небольшой дождь"),
    RAIN("Дождь"),
    POSSIBLE_RAIN("Возможен дождь"),
    RAIN_SHOWER("Кратковременный дождь"),
    THUNDERSTORM("Гроза"),
    LOCAL_THUNDERSTORMS("Местные грозы"),
    LIGHT_SNOW("Небольшой снег"),
    SNOW("Снег"),
    POSSIBLE_SNOW("Возможен снег"),
    SNOW_SHOWER("Кратковременный снег"),
    RAIN_AND_SNOW("Дождь со снегом"),
    POSSIBLE_RAIN_AND_SNOW("Возможен дождь со снегом"),
    FREEZING_RAIN("Замерзший дождь"),
    POSSIBLE_FREEZING_RAIN("Возможен замерзший дождь"),
    HAIL("Град"),
    CLEAR_NIGHT("Ясно (ночь)"),
    CLEAR("Ясно"),
    MOSTLY_CLEAR_NIGHT("Преимущественно ясно (ночь)"),
    MOSTLY_CLEAR("Преимущественно ясно"),
    PARTLY_CLEAR_NIGHT("Переменная облачность (ночь)"),
    PARTLY_CLEAR("Переменная облачность"),
    MOSTLY_CLOUDY_NIGHT("Преимущественно облачно (ночь)"),
    CLOUDY_NIGHT("Облачно (ночь)"),
    OVERCAST_LOW_CLOUDS_NIGHT("Пасмурно с низкими облаками (ночь)"),
    RAIN_SHOWER_NIGHT("Кратковременный дождь (ночь)"),
    LOCAL_THUNDERSTORMS_NIGHT("Местные грозы (ночь)"),
    SNOW_SHOWER_NIGHT("Кратковременный снег (ночь)"),
    RAIN_AND_SNOW_NIGHT("Дождь со снегом (ночь)"),
    POSSIBLE_RAIN_AND_SNOW_NIGHT("Возможен дождь со снегом (ночь)");

    private final String russianTranslation;

    private static final Map<String, WeatherCondition> BY_NAME = new HashMap<>();

    static {
        for (WeatherCondition condition : values()) {
            BY_NAME.put(condition.name().toLowerCase(), condition);
        }
    }

    WeatherCondition(String russianTranslation) {
        this.russianTranslation = russianTranslation;
    }

    public String getRussianTranslation() {
        return russianTranslation;
    }

    @JsonValue
    @Override
    public String toString() {
        return name().toLowerCase().replace("_", " ");
    }

    @JsonCreator
    public static WeatherCondition fromString(String name) {
        return BY_NAME.getOrDefault(name.toLowerCase().replace(" ", "_"), NOT_AVAILABLE);
    }
}
