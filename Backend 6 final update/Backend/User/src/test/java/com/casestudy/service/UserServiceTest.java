package com.casestudy.service;

import com.casestudy.model.BookingModel;
import com.casestudy.model.TrainModel;
import com.casestudy.model.UserModel;
import com.casestudy.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepo;

    @Mock
    private RestTemplate restTemplate;

    private UserModel user;
    private TrainModel train;

    @BeforeEach
    void setUp() {
        user = new UserModel();
        user.setId(1L);

        train = new TrainModel();
        train.setTrainNo("123");
        train.setFare(500);
    }

    @Test
    void testGetUserById() {
        Mockito.when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        Optional<UserModel> result = userService.getUserById(1L);
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testUpdateUser() {
        Mockito.when(userRepo.save(user)).thenReturn(user);
        String result = userService.updateUser("1", user);
        assertEquals("Updated Successfully", result);
    }

    @Test
    void testDeleteUser() {
        String result = userService.deleteUser(1L);
        Mockito.verify(userRepo).deleteById(1L);
        assertEquals("User with ID 1 deleted successfully", result);
    }

    @Test
    void testGetAllUsers() {
        Mockito.when(userRepo.findAll()).thenReturn(Arrays.asList(user));
        List<UserModel> users = userService.getAllUsers();
        assertEquals(1, users.size());
    }

    @Test
    void testGetTrainById() {
        Mockito.when(restTemplate.getForObject("http://TrainDetails/train/123", TrainModel.class))
                .thenReturn(train);
        TrainModel result = userService.getTrainById("123");
        assertEquals("123", result.getTrainNo());
    }

    @Test
    void testFindByLocation() {
        TrainModel[] trains = {train};
        Mockito.when(restTemplate.getForObject("http://TrainDetails/train/findbw/A/B", TrainModel[].class))
                .thenReturn(trains);
        List<TrainModel> result = userService.findByLocation("A", "B");
        assertEquals(1, result.size());
    }

    @Test
    void testBookTicket() {
        BookingModel booking = new BookingModel();
        booking.setTrainNo("123");
        booking.setUserId("u1");

        Mockito.when(restTemplate.getForObject("http://TrainDetails/train/123", TrainModel.class))
                .thenReturn(train);
        Mockito.when(restTemplate.postForObject("http://BOOKINGDETAILS/booking/bookticket/u1/500",
                booking, String.class)).thenReturn("Booked");

        String result = userService.bookTicket(booking);
        assertEquals("Booked", result);
    }

    @Test
    void testCancelTicket() {
        BookingModel booking = new BookingModel();
        booking.setPnrId("P123");
        booking.setTrainNo("123");
        booking.setTotalseats(3);

        Mockito.when(restTemplate.getForObject("http://BookingDetails/booking/getorderpnr/P123", BookingModel.class))
                .thenReturn(booking);
        Mockito.when(restTemplate.getForObject("http://TrainDetails/train/viewtrainbyno/123", TrainModel.class))
                .thenReturn(train);

        String result = userService.cancelTicket("P123");
        assertEquals("Train Ticket with PNR P123 canceled successfully", result);
    }
}