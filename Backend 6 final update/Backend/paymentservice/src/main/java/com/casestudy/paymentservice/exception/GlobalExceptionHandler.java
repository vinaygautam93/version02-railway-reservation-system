//package com.casestudy.paymentservice.exception;
//
//import com.stripe.exception.StripeException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(StripeException.class)
//    public ResponseEntity<String> handleStripeException(StripeException e) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment Error: " + e.getMessage());
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleGenericException(Exception e) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
//    }
//}
////