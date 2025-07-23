package com.casestudy.paymentservice.service;

import com.casestudy.paymentservice.dto.BookingResponse;
import com.casestudy.paymentservice.dto.EmailRequest;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StripeWebhookService {

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    private final RestTemplate restTemplate;

    public StripeWebhookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> processStripeEvent(String payload, String sigHeader) {
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.badRequest().body("Webhook signature verification failed");
        }

        if ("checkout.session.completed".equals(event.getType())) {
            handleCheckoutSessionCompleted(event);
        }

        return ResponseEntity.ok("Webhook received");
    }

    private void handleCheckoutSessionCompleted(Event event) {
        Session session = (Session) event.getDataObjectDeserializer().getObject().orElse(null);

        if (session != null) {
            String bookingId = session.getClientReferenceId();

            if (bookingId != null && !bookingId.isEmpty()) {
                try {
                    confirmBooking(bookingId);
                    sendEmailNotification(bookingId);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    // Log error or handle retry logic if needed
                }
            }
        }
    }

    private void confirmBooking(String bookingId) {
        String bookingConfirmUrl = "http://BOOKINGDETAILS/booking/confirm/" + bookingId;
        restTemplate.postForEntity(bookingConfirmUrl, null, Void.class);
    }

    private void sendEmailNotification(String bookingId) {
        String bookingDetailsUrl = "http://BOOKINGDETAILS/booking/getorderpnr/" + bookingId;
        BookingResponse booking = restTemplate.getForObject(bookingDetailsUrl, BookingResponse.class);

        if (booking != null && booking.getEmail() != null) {
            EmailRequest emailRequest = new EmailRequest(
                    booking.getEmail(),
                    "Booking Confirmed",
                    "Your ticket with booking ID " + bookingId + " has been successfully confirmed."
            );

            restTemplate.postForEntity("http://EMAIL-SERVICE/send", emailRequest, String.class);
        }
    }
}