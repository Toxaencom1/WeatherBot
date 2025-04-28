package com.taxah.weathersenderproject.exception;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String alreadyExistsMessage) {
        super(alreadyExistsMessage);
    }
}
