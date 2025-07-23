package com.casestudy.paymentservice.dto;
public class PaymentSuccessRequest {
    private String bookingId;
    private String userEmail;

    public PaymentSuccessRequest() {
        // Required for Spring to deserialize JSON
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return "PaymentSuccessRequest{" +
                "bookingId='" + bookingId + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}