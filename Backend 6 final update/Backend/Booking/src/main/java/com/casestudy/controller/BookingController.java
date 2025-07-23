package com.casestudy.controller;

import java.util.List;

import com.casestudy.model.BookingModel;
import com.casestudy.service.BookingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	private BookingService bookingService;

	Logger logger = LoggerFactory.getLogger(BookingController.class);

	@GetMapping("/get-latest-pnr")
	public String getLatestPnr() {
		System.out.println("frontend pnr");
		String latestPnr = bookingService.getLatestPnrId();
		return latestPnr != null ? latestPnr : "No bookings found";
	}



	@PostMapping("/bookticket/{userId}/{fare}")
	public ResponseEntity<?> bookTicket(@PathVariable String userId,
										@PathVariable int fare,
										@RequestBody BookingModel book) {
		logger.info("[bookTicket] Booking ticket for userId: {}, fare: {}", userId, fare);
		return bookingService.bookticket(userId, fare, book);
	}

	@GetMapping("/getallorders")
	public List<BookingModel> getAllOrders() {
		logger.info("[getAllOrders] Fetching all orders");
		return bookingService.getAllOrders();
	}

	@GetMapping("/getorder/{userId}")
	public List<BookingModel> getOrder(@PathVariable String userId) {
		logger.info("[getOrder] Fetching orders for userId: {}", userId);
		return bookingService.getOrdersByUserId(userId);
	}

	@GetMapping("/getorderpnr/{pnrId}")
	public BookingModel getOrderByPnr(@PathVariable String pnrId) {
		logger.info("[getOrderByPnr] Fetching order for pnrId: {}", pnrId);
		return bookingService.getOrderByPnrId(pnrId);
	}

	@DeleteMapping("/cancelticket/{pnrId}")
	public ResponseEntity<String> cancelTicket(@PathVariable String pnrId) {
		logger.info("[cancelTicket] Canceling ticket with pnrId: {}", pnrId);
		return bookingService.cancelTicket(pnrId);
	}

	@PatchMapping("/{pnrId}/status")
	public ResponseEntity<String> updateOrderStatus(@PathVariable String pnrId, @RequestParam String status) {
		logger.info("[updateOrderStatus] Updating status to '{}' for pnrId: {}", status, pnrId);
		return bookingService.updateOrderStatus(pnrId, status);
	}

	@PostMapping("/update-status/{bookingId}")
	public ResponseEntity<String> updateBookingStatus(@PathVariable String bookingId) {
		logger.info("[updateBookingStatus] Updating booking status for bookingId: {}", bookingId);
		return bookingService.updateBookingStatus(bookingId);
	}
}