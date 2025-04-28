package com.taxah.weathersenderproject.model.relay;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Timer {
    @JsonProperty("Enable")
    private Integer enable;
    @JsonProperty("Mode")
    private Integer mode;
    @JsonProperty("Time")
    private String time;
    @JsonProperty("Window")
    private Integer window;
    @JsonProperty("Days")
    private String days;
    @JsonProperty("Repeat")
    private Integer repeat;
    @JsonProperty("Output")
    private Integer output;
    @JsonProperty("Action")
    private Integer action;
}
