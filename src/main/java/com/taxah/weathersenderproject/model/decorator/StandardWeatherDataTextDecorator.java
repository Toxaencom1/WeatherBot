package com.taxah.weathersenderproject.model.decorator;

import com.taxah.weathersenderproject.model.weatherEntity.HourlyData;
import com.taxah.weathersenderproject.model.weatherEntity.WeatherEntry;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@Primary
public class StandardWeatherDataTextDecorator implements WeatherTextDecorator {

    @Override
    public String decorate(WeatherEntry weatherEntry) {
//        System.out.println(rd.toString());
        StringBuilder sb = new StringBuilder();
        sb.append("Погода в ")
                .append(StringUtils.capitalize(weatherEntry.getCityName()))
                .append(" на ближайшие сутки с шагом в 3 часа:\n\n");
        List<HourlyData> hourlyData = weatherEntry.getWeatherResponseData().getHourly().getData();
        for (int i = 0; i < hourlyData.size(); i = i + 3) {
            HourlyData data = hourlyData.get(i);
            sb
                    .append("Дата и время: ").append(data.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))).append("\n")
                    .append("Температура воздуха ожидается: ").append(data.getTemperature()).append("°C").append("\n")
                    .append("Погоду можно охарактеризовать как \"").append(data.getSummary().getRussianTranslation()).append("\"\n")
                    .append("Направление ветра - ").append(data.getWind().getDir()).append(" ")
                    .append("со скоростью ").append(data.getWind().getSpeed()).append("\n")
                    .append((data.getCloudCover() != null && data.getCloudCover().getTotal() != 0) ? "Небо закрыто тучами на " : "")
                    .append((data.getCloudCover() != null && data.getCloudCover().getTotal() != 0) ? data.getCloudCover().getTotal() + "%\n" : "")
                    .append((data.getCloudCover() != null && data.getCloudCover().getTotal() == 0) ? "Чистое небо над головой\n" : "")
                    .append("Осадки ").append(data.getPrecipitation().getTotal())
                    .append((data.getPrecipitation().getTotal() != 0) ? " единиц" : "")
                    .append((data.getPrecipitation().getTotal() != 0) ? " типа " : "")
                    .append((data.getPrecipitation().getTotal() != 0) ? data.getPrecipitation().getType().getRussianTranslation() : "")
                    .append("\n\n");
        }
        return sb.toString();
    }
}
