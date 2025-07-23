package com.casestudy.service;

import com.casestudy.model.BookingModel;
import com.casestudy.model.BookingStatus;
import com.casestudy.model.PaymentRequest;
import com.casestudy.model.TrainModel;
import com.casestudy.repository.BookingRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BookingService bookingService;

    private BookingModel booking;
    private TrainModel train;

    @BeforeEach
    void setup() {
        booking = new BookingModel();
        booking.setTrainNo("101");
        booking.setUserId("U001");
        booking.setEmail("user@example.com");
        booking.setTotalseats(2);
        booking.setPnrId("PNR123");

        train = new TrainModel();
        train.setTrainNo(101L);
        train.setSeats(10);
        train.setFare(300);
    }

    @Test
    void testBookTicket_SuccessfulPayment() {
        when(bookingRepository.findByUserId("U001")).thenReturn(List.of());
        when(restTemplate.getForObject("http://TrainDetails/train/101", TrainModel.class)).thenReturn(train);
        when(restTemplate.postForEntity(eq("http://PAYMENTSERVICE/payment/create-checkout-session"),
                any(PaymentRequest.class), eq(Map.class)))
                .thenReturn(ResponseEntity.ok(Map.of("checkoutUrl", "http://payment.com/pay")));

        ResponseEntity<?> response = bookingService.bookticket("U001", 500, booking);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("checkoutUrl"));
    }

    @Test
    void testBookTicket_InsufficientSeats() {
        train.setSeats(1); // less than requested
        when(bookingRepository.findByUserId("U001")).thenReturn(List.of());
        when(restTemplate.getForObject("http://TrainDetails/train/101", TrainModel.class)).thenReturn(train);

        ResponseEntity<?> response = bookingService.bookticket("U001", 500, booking);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Not enough seats available.", response.getBody());
    }

    @Test
    void testGetAllOrders() {
        when(bookingRepository.findAll()).thenReturn(List.of(booking));
        List<BookingModel> orders = bookingService.getAllOrders();
        assertEquals(1, orders.size());
    }

    @Test
    void testCancelTicket_BookingExists() {
        booking.setId(1L);
        when(bookingRepository.findByPnrId("PNR123")).thenReturn(booking);
        when(restTemplate.getForObject("http://TrainDetails/train/101", TrainModel.class)).thenReturn(train);

        ResponseEntity<String> result = bookingService.cancelTicket("PNR123");

        assertEquals(200, result.getStatusCodeValue());
        assertTrue(result.getBody().contains("cancelled successfully"));
        verify(bookingRepository).deleteById(1L);
    }

    @Test
    void testCancelTicket_BookingNotFound() {
        when(bookingRepository.findByPnrId("XYZ")).thenReturn(null);

        ResponseEntity<String> result = bookingService.cancelTicket("XYZ");

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertTrue(result.getBody().contains("No booking found for PNR ID"));
    }

    @Test
    void testUpdateOrderStatus() {
        when(bookingRepository.findByPnrId("PNR123")).thenReturn(booking);

        ResponseEntity<String> response = bookingService.updateOrderStatus("PNR123", "CONFIRMED");

        assertEquals("Order status updated to CONFIRMED", response.getBody());
        assertEquals(BookingStatus.CONFIRMED, booking.getStatus());
    }

    @Test
    void testUpdateBookingStatus_ValidBooking() {
        when(bookingRepository.findByPnrId("PNR123")).thenReturn(booking);

        ResponseEntity<String> response = bookingService.updateBookingStatus("PNR123");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Booking status updated to CONFIRMED", response.getBody());
        assertEquals(BookingStatus.CONFIRMED, booking.getStatus());
    }

    @Test
    void testUpdateBookingStatus_BookingNotFound() {
        when(bookingRepository.findByPnrId("ABC")).thenReturn(null);

        ResponseEntity<String> response = bookingService.updateBookingStatus("ABC");

        assertEquals(404, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Booking not found with ID"));
    }
}