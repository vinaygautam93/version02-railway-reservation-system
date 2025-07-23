// This is the package name where our test class lives
package com.casestudy.controller;

// We import our model and service class that we want to test with
import com.casestudy.model.BookingModel;
import com.casestudy.service.BookingService;

// These are libraries used for testing with JUnit 5
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

// These are for using Mockito to fake the service layer
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// Spring class used to send HTTP responses in tests
import org.springframework.http.ResponseEntity;

import java.util.List;

// These help us check test results and use mocking features
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// This connects JUnit and Mockito so we can use @Mock etc.
@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    // This creates the controller and puts the fake service inside it
    @InjectMocks
    private BookingController bookingController;

    // This is a fake version of BookingService, so real logic won't run
    @Mock
    private BookingService bookingService;

    // This is a sample booking we'll reuse in all tests
    private BookingModel booking;

    // This method runs before each test, setting up sample booking data
    @BeforeEach
    void setUp() {
        booking = new BookingModel();           // Make an empty booking
        booking.setPnrId("PNR001");             // Set fake PNR ID
        booking.setUserId("U101");              // Set fake user ID
        booking.setTrainNo("T123");             // Set fake train number
        booking.setTotalseats(2);               // Set number of booked seats
    }

    // Test #1: Controller should return a list with 1 booking
    @Test
    void testGetAllOrders() {
        when(bookingService.getAllOrders()).thenReturn(List.of(booking)); // Service gives one booking
        List<BookingModel> result = bookingController.getAllOrders();     // Call the controller
        assertEquals(1, result.size());                                    // Check if size = 1
    }

    // Test #2: Get orders for a specific user
    @Test
    void testGetOrder() {
        when(bookingService.getOrdersByUserId("U101")).thenReturn(List.of(booking)); // Fake result by service
        List<BookingModel> result = bookingController.getOrder("U101");              // Controller method
        assertEquals("U101", result.get(0).getUserId());                             // Check user ID
    }

    // Test #3: Get booking using a PNR ID
    @Test
    void testGetOrderByPnr() {
        when(bookingService.getOrderByPnrId("PNR001")).thenReturn(booking);  // Return one booking
        BookingModel result = bookingController.getOrderByPnr("PNR001");     // Ask controller for that PNR
        assertEquals("PNR001", result.getPnrId());                           // Check if PNR is correct
    }

    // Test #4: Cancel a ticket and get response "Cancelled"
    @Test
    void testCancelTicket() {
        when(bookingService.cancelTicket("PNR001")).thenReturn(ResponseEntity.ok("Cancelled")); // Fake service
        ResponseEntity<String> response = bookingController.cancelTicket("PNR001");              // Call controller
        assertEquals("Cancelled", response.getBody());                                           // Check output
    }

    //  Test #5: Update ticket status, expect response "Updated"
    @Test
    void testUpdateOrderStatus() {
        when(bookingService.updateOrderStatus("PNR001", "CONFIRMED")).thenReturn(ResponseEntity.ok("Updated"));
        ResponseEntity<String> response = bookingController.updateOrderStatus("PNR001", "CONFIRMED");
        assertEquals("Updated", response.getBody());
    }

    // Test #6: Update booking status and expect "CONFIRMED"
    @Test
    void testUpdateBookingStatus() {
        when(bookingService.updateBookingStatus("PNR001")).thenReturn(ResponseEntity.ok("CONFIRMED"));
        ResponseEntity<String> response = bookingController.updateBookingStatus("PNR001");
        assertEquals("CONFIRMED", response.getBody());
    }

    //  Extra Test (commented for now): To test ticket booking functionality
//    @Test
//    void testBookTicket() {
//        when(bookingService.bookticket("U101", 150, booking)).thenReturn(ResponseEntity.ok("Booked"));
//        ResponseEntity<?> response = bookingController.bookTicket("U101", 150, booking);
//        assertEquals("Booked", response.getBody());
//    }
}