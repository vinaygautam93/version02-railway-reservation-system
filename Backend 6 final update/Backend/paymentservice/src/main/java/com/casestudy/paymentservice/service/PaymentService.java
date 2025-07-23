package com.casestudy.paymentservice.service;

import com.casestudy.paymentservice.dto.EmailRequest;
import com.casestudy.paymentservice.dto.PaymentRequest;
import com.casestudy.paymentservice.dto.PaymentSuccessRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    @Value("${stripe.api.key}")
    private String stripeKey;

    private final RestTemplate restTemplate;

    public PaymentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<Map<String, String>> createCheckoutSession(PaymentRequest request) {
        Stripe.apiKey = stripeKey;

        try {
            SessionCreateParams.LineItem.PriceData.ProductData productData =
                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                            .setName("Ticket Payment for Booking ID: " + request.getBookingId())
                            .build();

            SessionCreateParams.LineItem.PriceData priceData =
                    SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency("inr")
                            .setUnitAmount((long) request.getAmount() * 100)
                            .setProductData(productData)
                            .build();

            SessionCreateParams.LineItem lineItem =
                    SessionCreateParams.LineItem.builder()
                            .setPriceData(priceData)
                            .setQuantity(1L)
                            .build();

            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .addLineItem(lineItem)
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setClientReferenceId(request.getBookingId()) // Track booking in webhook
                            .setSuccessUrl("http://localhost:3000/success")
                            .setCancelUrl("http://localhost:3000/cancel")
                            .build();

            Session session = Session.create(params);

            Map<String, String> responseData = new HashMap<>();
            responseData.put("checkoutUrl", session.getUrl());
            responseData.put("sessionId", session.getId());

            return ResponseEntity.ok(responseData);

        } catch (StripeException e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    public ResponseEntity<String> confirmBooking(PaymentSuccessRequest request) {
        System.out.println("Request object: " + request);
        System.out.println("Booking ID: " + request.getBookingId());
        System.out.println("User Email: " + request.getUserEmail());


        String bookingId = request.getBookingId();
        System.out.println(request.getUserEmail());
        String email = request.getUserEmail();
        System.out.println(request.getUserEmail());

        // 1. Update Booking Status to Confirmed
       // restTemplate.postForEntity("http://BOOKINGDETAILS/booking/update-status/" + bookingId, null, Void.class);

        try {
            restTemplate.postForEntity("http://BOOKINGDETAILS/booking/update-status/" + bookingId, null, Void.class);
        } catch (Exception e) {
            System.out.println("Booking update failed: " + e.getMessage());
        }

        // 2. Send Confirmation Email
        EmailRequest emailRequest = new EmailRequest();
        System.out.println(request.getUserEmail());
        emailRequest.setTo(email);
        emailRequest.setSubject("Booking Confirmed - PNR " + bookingId);
        emailRequest.setBody("Your payment for PNR " + bookingId + " is successful. Your ticket is confirmed!");

      //  restTemplate.postForEntity("http://EMAIL-SERVICE/send", emailRequest, Void.class);

        try {
            System.out.println(request.getUserEmail());
            restTemplate.postForEntity("http://EMAIL-SERVICE/send", emailRequest, Void.class);
        } catch (Exception e) {
            System.out.println("Email sending failed: " + e.getMessage());
        }

        return ResponseEntity.ok("Booking confirmed and email sent");
    }
}