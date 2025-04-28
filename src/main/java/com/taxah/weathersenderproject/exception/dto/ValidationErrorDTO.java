package com.taxah.weathersenderproject.exception.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ValidationErrorDTO {
    private final UUID errorUUID;
    private final List<String> errors;
}
