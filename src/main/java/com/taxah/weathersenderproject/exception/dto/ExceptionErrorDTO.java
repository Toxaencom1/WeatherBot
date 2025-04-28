package com.taxah.weathersenderproject.exception.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ExceptionErrorDTO {
    private final UUID errorUUID;
    private String errorMessage;
    private final String trace;
}
