package com.taxah.weathersenderproject.model.weatherObjects;

import com.taxah.weathersenderproject.model.enums.PrecipitationType;
import lombok.Data;

@Data
public class Precipitation {
    private double total;
    private PrecipitationType type;
}
