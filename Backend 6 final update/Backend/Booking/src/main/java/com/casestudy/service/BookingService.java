
package com.casestudy.service;

import com.casestudy.exception.BookingException;
import com.casestudy.exception.InsufficientSeatsException;
import com.casestudy.exception.TrainNotFoundException;
import com.casestudy.model.BookingModel;
import com.casestudy.model.BookingStatus;
import com.casestudy.model.PaymentRequest;
import com.casestudy.model.TrainModel;
import com.casestudy.repository.BookingRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RestTemplate restTemplate;


    public String getLatestPnrId() {
        BookingModel latestBooking = bookingRepository.findLatestBooking();
        return latestBooking != null ? latestBooking.getPnrId() : null;
    }



    public ResponseEntity<?> bookticket(String userId, int fare, BookingModel book) {
        logger.info("Attempting to book ticket for userId: {}", userId);

        book.setUserId(userId);
        // ✅ Auto-generate PNR here
        String generatedPnr = PnrGenerator.generatePNR();
        book.setPnrId(generatedPnr);

        List<BookingModel> ticketslist = bookingRepository.findByUserId(userId);

        int totalseatsBooked = ticketslist.stream().mapToInt(BookingModel::getTotalseats).sum();

        // Step 1: Fetch Train Details
        String trainServiceUrl = "http://TrainDetails/train/" + book.getTrainNo();
        TrainModel train = restTemplate.getForObject(trainServiceUrl, TrainModel.class);

        if (train == null) {
            logger.warn("Train not found for trainNo: {}", book.getTrainNo());
            throw new TrainNotFoundException("Train not found for trainNo: " + book.getTrainNo());
        }

        if (train.getSeats() < book.getTotalseats()) {
            logger.warn("Not enough seats available for trainNo: {}", book.getTrainNo());
            throw new InsufficientSeatsException("Not enough seats available.");
        }

        // Step 2: Update train seat count
        train.setSeats(train.getSeats() - book.getTotalseats());
        restTemplate.put("http://TRAINDETAILS/train/updateSeats/" + train.getTrainNo(), train);
        logger.info("Updated seat count for trainNo: {}", train.getTrainNo());

        // Step 3: Save booking
        book.setFare(book.getTotalseats() * fare);
        book.setStatus(BookingStatus.PENDING);
        bookingRepository.save(book);
        logger.info("Booking created with PNR: {}", book.getPnrId());

        // Step 4: Call payment-service
        PaymentRequest paymentRequest = new PaymentRequest(book.getPnrId(), book.getFare(), book.getEmail());

        ResponseEntity<Map> paymentResponse = restTemplate.postForEntity(
                "http://PAYMENTSERVICE/payment/create-checkout-session",
                paymentRequest,
                Map.class
        );

        if (paymentResponse.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> responseBody = paymentResponse.getBody();
            if (responseBody != null && responseBody.containsKey("checkoutUrl")) {
                String checkoutUrl = (String) responseBody.get("checkoutUrl");
                logger.info("Checkout URL received for booking with PNR: {}", book.getPnrId());
                return ResponseEntity.ok(Map.of("checkoutUrl", checkoutUrl));
            } else {
                logger.error("Payment service did not return a checkout URL");
                throw new BookingException("Payment service did not return a checkout URL.");
            }
        } else {
            logger.error("Payment failed for booking with PNR: {}, cancelling booking", book.getPnrId());
            book.setStatus(BookingStatus.CANCELLED);
            bookingRepository.save(book);

            train.setSeats(train.getSeats() + book.getTotalseats());
            restTemplate.put("http://TrainDetails/train/updateSeats/" + train.getTrainNo(), train);

            throw new BookingException("Payment failed. Booking cancelled.");
        }
    }

    public List<BookingModel> getAllOrders() {
        logger.debug("Fetching all booking orders");
        return bookingRepository.findAll();
    }

    public List<BookingModel> getOrdersByUserId(String userId) {
        logger.debug("Fetching bookings for userId: {}", userId);
        return bookingRepository.findByUserId(userId);
    }

    public BookingModel getOrderByPnrId(String pnrId) {
        logger.debug("Fetching booking for pnrId: {}", pnrId);
        return bookingRepository.findByPnrId(pnrId);
    }

    @Transactional
    public ResponseEntity<String> cancelTicket(String pnrId) {
        logger.info("Attempting to cancel ticket with pnrId: {}", pnrId);
        try {
            BookingModel booking = bookingRepository.findByPnrId(pnrId);
            if (booking == null) {
                logger.warn("No booking found for PNR ID: {}", pnrId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No booking found for PNR ID " + pnrId);
            }

            booking.setStatus(BookingStatus.CANCELLED);
            bookingRepository.save(booking);

            TrainModel train = restTemplate.getForObject(
                    "http://TrainDetails/train/" + booking.getTrainNo(), TrainModel.class);

            if (train != null) {
                train.setSeats(train.getSeats() + booking.getTotalseats());
               // restTemplate.put("http://TrainDetails/train/updateSeats/" + train.getTrainNo(), train);
            }

         //   bookingRepository.deleteById(booking.getId());
            logger.info("Booking with PNR ID: {} cancelled successfully", pnrId);
            return ResponseEntity.ok("Train Ticket with PNR " + pnrId + " cancelled successfully");
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error cancelling ticket. PNR ID {} does not exist", pnrId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("PNR ID " + pnrId + " does not exist!");
        }
    }

    public ResponseEntity<String> updateOrderStatus(String pnrId, String status) {
        logger.info("Updating booking status for PNR: {} to {}", pnrId, status);
        BookingModel booking = bookingRepository.findByPnrId(pnrId);
        booking.setStatus(BookingStatus.valueOf(status));
        bookingRepository.save(booking);
        return ResponseEntity.ok("Order status updated to " + status);
    }

    public ResponseEntity<String> updateBookingStatus(String bookingId) {
        logger.info("Confirming booking status for ID: {}", bookingId);
        BookingModel booking = bookingRepository.findByPnrId(bookingId);
        if (booking == null) {
            logger.warn("Booking ID {} not found", bookingId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Booking not found with ID: " + bookingId);
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);
        logger.info("Booking status confirmed for ID: {}", bookingId);
        return ResponseEntity.ok("Booking status updated to CONFIRMED");
    }
}
