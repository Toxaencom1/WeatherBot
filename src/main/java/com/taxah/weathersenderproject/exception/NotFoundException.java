package com.taxah.weathersenderproject.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String notFoundMessage) {
        super(notFoundMessage);
    }
}
