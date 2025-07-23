package com.casestudy.controller;

import com.casestudy.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.casestudy.model.BookingModel;
import com.casestudy.model.TrainModel;
import com.casestudy.model.UserModel;
import com.casestudy.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class  UserController {

    @Autowired
    private UserService userService;
    @Autowired
    BookingRepository bookingrepo;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    // Fetch user profile by ID
    @GetMapping("/viewuserprofile/{id}")
    public Optional<UserModel> getUser(@PathVariable("id") Long id) {
        logger.info("[viewuserprofile/Id] Fetching user profile for ID {}", id);
        return userService.getUserById(id);
    }

    @GetMapping("/getorder/{userId}")
    public List<BookingModel> getUserOrders(@PathVariable("userId") String userId) {
        logger.info("[getorder/userId] Fetching bookings for user {}", userId);
        return bookingrepo.findByUserId(userId);
    }

    @GetMapping("/findbw/{trainFrom}/{trainTo}")
    public List<TrainModel> findByLocation(@PathVariable String trainFrom, @PathVariable String trainTo) {
        return userService.findByLocation(trainFrom, trainTo);
    }


    // Update user profile
    @PutMapping("/updateprofile/{id}")
    public String updateUser(@PathVariable("id") String id, @RequestBody UserModel userModel) {
        logger.info("[updateprofile/Id] Updating profile for ID {}", id);
        return userService.updateUser(id, userModel);
    }

    // Delete user profile
    @DeleteMapping("/deleteprofile/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        logger.info("[deleteprofile/Id] Deleting profile for ID {}", id);
        return userService.deleteUser(id);
    }

    // Fetch all users
    @GetMapping("/viewallusers")
    public List<UserModel> getAllUsers() {
        logger.info("[viewallusers] Fetching all user profiles");
        return userService.getAllUsers();
    }

    // Fetch train details
    @GetMapping("/viewalltrains")
    public List<TrainModel> getAllTrains() {
        logger.info("[viewalltrains] Fetching all train details");
        return userService.getAllTrains();
    }

    @GetMapping("/viewtrain/{trainNo}")
    public TrainModel getTrainById(@PathVariable("trainNo") String trainNo) {
        logger.info("[viewtrain/trainId] Fetching train details for ID {}", trainNo);
        return userService.getTrainById(trainNo);
    }

    @PostMapping("/bookticket")
    public String bookTicket(@RequestBody BookingModel bookingModel) {
        logger.info("[bookticket] Booking train ticket");
        return userService.bookTicket(bookingModel);
    }

    @DeleteMapping("/cancelticket/{pnrId}")
    public String cancelTicket(@PathVariable("pnrId") String pnrId) {
        logger.info("[cancelticket/pnrId] Canceling ticket for PNR {}", pnrId);
        return userService.cancelTicket(pnrId);
    }
}