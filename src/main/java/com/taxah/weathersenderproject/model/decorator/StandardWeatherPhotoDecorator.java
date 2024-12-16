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
import java.util.List;

@Component
@Primary
public class StandardWeatherPhotoDecorator implements WeatherPhotoDecorator {
    @Override
    public InputFile decorate(WeatherResponseData rd) {
        if (rd != null) {
            List<HourlyData> hourlyData = rd.getHourly().getData();
            int height = 100;
            int width = 1050;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, width, height);
            g.setColor(Color.WHITE);
            Font monoFont = new Font("Courier New", Font.PLAIN, 10);
            g.setFont(monoFont);

            Font font = g.getFont();
            System.out.println("Font name: " + font.getName());
            System.out.println("Font family: " + font.getFamily());

            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            StringBuilder sb3 = new StringBuilder();
            StringBuilder sb4 = new StringBuilder();
            StringBuilder sb5 = new StringBuilder();

            int x = 10;
            int y = 30;
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

            System.out.println(sb1);
            System.out.println(sb2);
            System.out.println(sb3);
            System.out.println(sb4);
            System.out.println(sb5);

            File outputFile = new File("src/main/resources/static/weatherTable.png");
            try {
                ImageIO.write(image, "png", outputFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return new InputFile(outputFile);
        }else {
            return null;
        }
    }
}
