package com.taxah.weathersenderproject.model.decorator;

import com.taxah.weathersenderproject.model.weatherEntity.HourlyData;
import com.taxah.weathersenderproject.model.weatherEntity.WeatherResponseData;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@Primary
public class StandardWeatherPhotoDecorator implements WeatherPhotoDecorator {
    @Override
    public InputFile decorate(WeatherResponseData rd) {
        System.out.println("Decorating message media content... ");
        if (rd != null) {
            List<HourlyData> hourlyData = rd.getHourly().getData();
            int height = 310;
            int width = 330;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, width, height);
            g.setColor(Color.WHITE);
            Font customFont;
            try (InputStream fontStream = getClass().getResourceAsStream("/fonts/couriernew.ttf")) {
                if (fontStream == null) {
                    throw new RuntimeException("Шрифт не найден в ресурсах.");
                }
                customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(10f);
            } catch (FontFormatException | IOException e) {
                throw new RuntimeException("Ошибка при загрузке шрифта.", e);
            }
            g.setFont(customFont);

            int y = 30;

            int prev;
            int step = 6;
            for (int i = 0; i < hourlyData.size(); i = i + step) {
                prev = i;
                decoratePart(hourlyData.subList(prev, prev + step), g, y);
                y+=70;
            }

            File outputFile = new File("src/main/resources/static/weatherTable.png");

            File parentDir = outputFile.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();  // Создает все необходимые родительские директории
            }
            try {
                ImageIO.write(image, "png", outputFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return new InputFile(outputFile);
        } else {
            return null;
        }
    }

    private void decoratePart(List<HourlyData> hourlyData, Graphics2D g, int y) {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        StringBuilder sb4 = new StringBuilder();
        StringBuilder sb5 = new StringBuilder();

        int x = 10;

        int step = 12;

        String d1 = "|=====|".repeat(hourlyData.size() - 2);
        sb1.append("||====Hours===|").append(d1).append("|");
        g.drawString(sb1.toString(), x, y);
        y += step;

        sb2.append("|");
        for (HourlyData hourlyDatum : hourlyData) {
            String hour = "|%5d|";
            sb2.append(String.format(hour, hourlyDatum.getDate().getHour()));
        }
        sb2.append("|");
        g.drawString(sb2.toString(), x, y);
        y += step;

        String d3 = "|=====|".repeat(hourlyData.size() - 2);
        sb3.append("||=Temperature|").append(d3).append("|");
        g.drawString(sb3.toString(), x, y);
        y += step;

        sb4.append("|");
        for (HourlyData hourlyDatum : hourlyData) {
            String hour = "|%5.1f|";
            sb4.append(String.format(hour, hourlyDatum.getTemperature()));
        }
        sb4.append("|");
        g.drawString(sb4.toString(), x, y);
        y += step;

        String d4 = "|=====|".repeat(hourlyData.size());
        sb5.append("|").append(d4).append("|");
        g.drawString(sb5.toString(), x, y);
    }
}
