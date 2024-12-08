package com.taxah.weathersenderproject.model.weatherObjects;

import lombok.Data;

@Data
public class Wind {
    private double speed;
    private int angle;
    private String dir;
}
