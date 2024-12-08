package com.taxah.weathersenderproject.model.decorator;

import com.taxah.weathersenderproject.model.WeatherResponseData;
import com.taxah.weathersenderproject.model.weatherObjects.HourlyData;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class StandardWeatherDataDecorator {
    public static String decorate(WeatherResponseData rd) {
        StringBuilder sb = new StringBuilder();
        sb.append("Погода в Алматы на ближайшие 24 часа с шагом в 3 часа:\n\n");
        List<HourlyData> hourlyData = rd.getHourly().getData();
        for (int i = 0; i < hourlyData.size(); i = i + 3) {
            HourlyData data = hourlyData.get(i);
            sb.append("Дата и время: ").append(data.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))).append("\n")
                    .append("Погоду можно охарактеризовать как \"").append(data.getSummary().getRussianTranslation()).append("\"\n")
                    .append("Температура: ").append(data.getTemperature()).append("\n")
                    .append("Направление ветра - ").append(data.getWind().getDir()).append(" ")
                    .append("со скоростю ").append(data.getWind().getSpeed()).append("\n")
                    .append("Осадки ").append(data.getPrecipitation().getTotal())
                    .append((data.getPrecipitation().getTotal() != 0) ? " единиц" : "")
                    .append((data.getPrecipitation().getTotal() != 0) ? " типа " : "")
                    .append((data.getPrecipitation().getTotal() != 0) ? data.getPrecipitation().getType().getRussianTranslation() : "").append("\n")
                    .append((data.getCloudCover() != null) ? "типа " : "")
                    .append((data.getCloudCover() != null) ? data.getCloudCover() : "")
                    .append("\n")
            ;
        }
        return sb.toString();
    }
}
