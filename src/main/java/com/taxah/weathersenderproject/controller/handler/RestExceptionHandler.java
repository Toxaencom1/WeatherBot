package com.taxah.weathersenderproject.controller.handler;

import com.taxah.weathersenderproject.exception.dto.StringErrorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;

import java.util.UUID;

@ControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<StringErrorDTO> handleResourceAccessException(ResourceAccessException e) {
        return stringResponseEntity(e, HttpStatus.FORBIDDEN, null);
    }

    private ResponseEntity<StringErrorDTO> stringResponseEntity(Exception e, HttpStatus status, String extra) {
        return ResponseEntity.status(status).body(
                StringErrorDTO.builder()
                        .errorUUID(UUID.randomUUID())
                        .errorMessage(e.getMessage())
                        .build()
        );
    }
}
