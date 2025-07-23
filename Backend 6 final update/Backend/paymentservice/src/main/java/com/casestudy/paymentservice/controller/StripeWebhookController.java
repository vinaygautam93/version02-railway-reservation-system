//package com.casestudy.paymentservice.controller;
//
//
//import com.casestudy.paymentservice.dto.BookingResponse;
//import com.casestudy.paymentservice.dto.EmailRequest;
//import com.stripe.exception.SignatureVerificationException;
//import com.stripe.model.Event;
//import com.stripe.model.checkout.Session;
//import com.stripe.net.Webhook;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//
//@RestController
//@RequestMapping("/webhook")
//public class StripeWebhookController {
//
//    @Value("${stripe.webhook.secret}")
//    private String endpointSecret;
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @PostMapping
//    public ResponseEntity<String> handleStripeEvent(@RequestBody String payload,
//                                                    @RequestHeader("Stripe-Signature") String sigHeader) {
//        Event event;
//
//        try {
//            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
//        } catch (SignatureVerificationException e) {
//            return ResponseEntity.badRequest().body("Webhook signature verification failed");
//        }
//
//        // Handle checkout session completed event
//        if ("checkout.session.completed".equals(event.getType())) {
//            Session session = (Session) event.getDataObjectDeserializer().getObject().orElse(null);
//
//            if (session != null) {
//                String bookingId = session.getClientReferenceId();
//
//                if (bookingId != null && !bookingId.isEmpty()) {
//                    try {
//                        // Confirm booking by calling booking service
//                        String bookingConfirmUrl = "http://BOOKINGDETAILS/booking/confirm/" + bookingId;
//                        restTemplate.postForEntity(bookingConfirmUrl, null, Void.class);
//
//                        // Send email notification via email service
//                        // Ideally, booking service returns email, but here assume email passed as client_reference_id or fetched somehow
//                        // For demo, let's assume booking service exposes GET booking by id
//                        String bookingDetailsUrl = "http://BOOKINGDETAILS/booking/getorderpnr/" + bookingId;
//                        BookingResponse booking = restTemplate.getForObject(bookingDetailsUrl, BookingResponse.class);
//
//                        if (booking != null && booking.getEmail() != null) {
//                            EmailRequest emailRequest = new EmailRequest(
//                                    booking.getEmail(),
//                                    "Booking Confirmed",
//                                    "Your ticket with booking ID " + bookingId + " has been successfully confirmed."
//                            );
//
//                            restTemplate.postForEntity("http://EMAIL-SERVICE/send", emailRequest, String.class);
//                        }
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                        // Log error or handle retry logic here if needed
//                    }
//                }
//            }
//        }
//
//        return ResponseEntity.ok("Webhook received");
//    }
//
//
//}
package com.casestudy.paymentservice.controller;

import com.casestudy.paymentservice.service.StripeWebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
public class StripeWebhookController {

    @Autowired
    private StripeWebhookService stripeWebhookService;

    @PostMapping
    public ResponseEntity<String> handleStripeEvent(@RequestBody String payload,
                                                    @RequestHeader("Stripe-Signature") String sigHeader) {
        return stripeWebhookService.processStripeEvent(payload, sigHeader);
    }
}