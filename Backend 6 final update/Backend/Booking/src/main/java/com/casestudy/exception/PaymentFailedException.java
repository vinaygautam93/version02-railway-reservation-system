package com.casestudy.exception;

public class PaymentFailedException extends BookingException {
    public PaymentFailedException(String message) {
        super(message);
    }
}
