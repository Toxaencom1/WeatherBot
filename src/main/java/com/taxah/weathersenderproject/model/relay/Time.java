package com.taxah.weathersenderproject.model.relay;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Time {
    @JsonProperty("Time")
    private String time;
}
