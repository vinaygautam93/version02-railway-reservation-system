package com.casestudy.exception;

public class InsufficientSeatsException extends BookingException {
    public InsufficientSeatsException(String message) {
        super(message);
    }
}
