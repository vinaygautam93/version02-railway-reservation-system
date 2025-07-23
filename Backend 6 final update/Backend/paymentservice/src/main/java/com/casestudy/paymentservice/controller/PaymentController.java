//package com.casestudy.paymentservice.controller;
//
//import com.casestudy.paymentservice.dto.EmailRequest;
//import com.casestudy.paymentservice.dto.PaymentRequest;
//import com.casestudy.paymentservice.dto.PaymentSuccessRequest;
//import com.stripe.Stripe;
//import com.stripe.exception.StripeException;
//import com.stripe.model.checkout.Session;
//import com.stripe.param.checkout.SessionCreateParams;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/payment")
//public class PaymentController {
//
//    @Value("${stripe.api.key}")
//    private String stripeKey;
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @PostMapping("/create-checkout-session")
//    public ResponseEntity<Map<String, String>> createCheckoutSession(@RequestBody PaymentRequest request) {
//        Stripe.apiKey = stripeKey;
//
//        try {
//            SessionCreateParams.LineItem.PriceData.ProductData productData =
//                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
//                            .setName("Ticket Payment for Booking ID: " + request.getBookingId())
//                            .build();
//
//            SessionCreateParams.LineItem.PriceData priceData =
//                    SessionCreateParams.LineItem.PriceData.builder()
//                            .setCurrency("inr")
//                            .setUnitAmount((long) request.getAmount() * 100)
//                            .setProductData(productData)
//                            .build();
//
//            SessionCreateParams.LineItem lineItem =
//                    SessionCreateParams.LineItem.builder()
//                            .setPriceData(priceData)
//                            .setQuantity(1L)
//                            .build();
//
//            SessionCreateParams params =
//                    SessionCreateParams.builder()
//                            .addLineItem(lineItem)
//                            .setMode(SessionCreateParams.Mode.PAYMENT)
//                            .setClientReferenceId(request.getBookingId()) //  Track booking in webhook
//                            .setSuccessUrl("http://localhost:4200/success")
//                            .setCancelUrl("http://localhost:4200/cancel")
//                            .build();
//
//            Session session = Session.create(params);
//
//            Map<String, String> responseData = new HashMap<>();
//            responseData.put("checkoutUrl", session.getUrl());
//            responseData.put("sessionId", session.getId());
//
//            return ResponseEntity.ok(responseData);
//
//        } catch (StripeException e) {
//            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
//        }
//
//    }
//        @PostMapping("/confirm-booking")
//        public ResponseEntity<String> confirmBooking (@RequestBody PaymentSuccessRequest request){
//            String bookingId = request.getBookingId();
//            String email = request.getUserEmail();
//
//            // 1. Update Booking Status to Confirmed
//            restTemplate.postForEntity("http://BOOKINGDETAILS/booking/update-status/" + bookingId, null, Void.class);
//
//            // 2. Send Confirmation Email
//            EmailRequest emailRequest = new EmailRequest();
//            emailRequest.setTo(email);
//            emailRequest.setSubject("Booking Confirmed - PNR " + bookingId);
//            emailRequest.setBody("Your payment for PNR " + bookingId + " is successful. Your ticket is confirmed!");
//
//            restTemplate.postForEntity("http://EMAIL-SERVICE/send", emailRequest, Void.class);
//
//            return ResponseEntity.ok("Booking confirmed and email sent");
//        }
//
//
//

package com.casestudy.paymentservice.controller;

import com.casestudy.paymentservice.dto.PaymentRequest;
import com.casestudy.paymentservice.dto.PaymentSuccessRequest;
import com.casestudy.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create-checkout-session")
    public ResponseEntity<Map<String, String>> createCheckoutSession(@RequestBody PaymentRequest request) {
        return paymentService.createCheckoutSession(request);
    }

    @PostMapping("/confirm-booking")
    public ResponseEntity<String> confirmBooking(@RequestBody PaymentSuccessRequest request) {
        return paymentService.confirmBooking(request);
    }
}