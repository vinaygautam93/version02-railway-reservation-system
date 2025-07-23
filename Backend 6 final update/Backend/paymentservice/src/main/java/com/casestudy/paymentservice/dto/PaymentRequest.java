package com.casestudy.paymentservice.dto;

public class PaymentRequest {
    private String bookingId;
    private int amount;
    private String email;

    public PaymentRequest(String bookingId, int amount, String email) {
        this.bookingId = bookingId;
        this.amount = amount;
        this.email = email;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
