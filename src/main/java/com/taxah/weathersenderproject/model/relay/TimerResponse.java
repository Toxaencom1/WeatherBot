package com.taxah.weathersenderproject.model.relay;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TimerResponse {
    @JsonProperty("Timer2")
    private Timer timer2;
}
