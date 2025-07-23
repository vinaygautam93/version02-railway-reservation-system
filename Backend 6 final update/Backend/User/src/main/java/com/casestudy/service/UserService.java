package com.casestudy.service;

import com.casestudy.model.BookingModel;
import com.casestudy.model.TrainModel;
import com.casestudy.model.UserModel;
import com.casestudy.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RestTemplate restTemplate;

    public Optional<UserModel> getUserById(Long id) {
        logger.info("Fetching user by ID: {}", id);
        return userRepo.findById(id);
    }

    public String updateUser(String id, UserModel userModel) {
        logger.info("Updating user with ID: {}", id);
        userRepo.save(userModel);
        return "Updated Successfully";
    }

    public String deleteUser(Long id) {
        logger.info("Deleting user with ID: {}", id);
        userRepo.deleteById(id);
        return "User with ID " + id + " deleted successfully";
    }

    public List<UserModel> getAllUsers() {
        logger.info("Retrieving all users");
        return userRepo.findAll();
    }

    public List<TrainModel> getAllTrains() {
        logger.info("Fetching all trains from TrainDetails service");
        return Arrays.asList(restTemplate.getForObject("http://TrainDetails/train/viewalltrains", TrainModel[].class));
    }

    public TrainModel getTrainById(String trainNo) {
        logger.info("Fetching train details for trainNo: {}", trainNo);
        return restTemplate.getForObject("http://TrainDetails/train/" + trainNo, TrainModel.class);
    }
    public List<TrainModel> findByLocation(String trainFrom, String trainTo) {
        logger.info("Calling TrainDetails service for: {} to {}", trainFrom, trainTo);

        String url = String.format("http://TrainDetails/train/findbw/%s/%s", trainFrom, trainTo);
        TrainModel[] trains = restTemplate.getForObject(url, TrainModel[].class);

        return Arrays.asList(trains);
    }

    public String bookTicket(BookingModel bookingModel) {
        logger.info("Booking ticket for userId: {}, trainNo: {}", bookingModel.getUserId(), bookingModel.getTrainNo());
        TrainModel trainModel = restTemplate.getForObject("http://TrainDetails/train/" + bookingModel.getTrainNo(), TrainModel.class);
        int fare = trainModel.getFare();
        String response = restTemplate.postForObject(
                "http://BOOKINGDETAILS/booking/bookticket/" + bookingModel.getUserId() + "/" + fare,
                bookingModel, String.class);
        logger.info("Booking response received: {}", response);
        return response;
    }

    public String cancelTicket(String pnrId) {
        logger.info("Initiating cancellation for ticket with PNR: {}", pnrId);
        BookingModel bookingModel = restTemplate.getForObject("http://BookingDetails/booking/getorderpnr/" + pnrId, BookingModel.class);
        TrainModel trainModel = restTemplate.getForObject("http://TrainDetails/train/" + bookingModel.getTrainNo(), TrainModel.class);

        restTemplate.delete("http://BookingDetails/booking/cancelticket/" + pnrId);
        logger.info("Booking cancelled for PNR: {}", pnrId);

        restTemplate.postForObject(
                "http://TrainDetails/train/increaseseat/" + trainModel.getTrainNo() + "/" + bookingModel.getTotalseats(),
                bookingModel, BookingModel.class);
        logger.info("Seats restored to trainNo: {}", trainModel.getTrainNo());

        return "Train Ticket with PNR " + pnrId + " canceled successfully";
    }
}