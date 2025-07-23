package com.casestudy.controller;
import com.casestudy.model.BookingModel;
import com.casestudy.model.TrainModel;
import com.casestudy.model.UserModel;
import com.casestudy.repository.BookingRepository;
import com.casestudy.service.UserService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private BookingRepository bookingRepository;

    @Test
    void testGetUser() {
        Long id = 1L;
        UserModel mockUser = new UserModel(); // set fields if needed
        Mockito.when(userService.getUserById(id)).thenReturn(Optional.of(mockUser));

        Optional<UserModel> result = userController.getUser(id);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(mockUser, result.get());
    }

    @Test
    void testFindByLocation() {
        List<TrainModel> trainList = Arrays.asList(new TrainModel(), new TrainModel());
        Mockito.when(userService.findByLocation("Bhopal", "Multai")).thenReturn(trainList);

        List<TrainModel> result = userController.findByLocation("Bhopal", "Multai");

        Assertions.assertEquals(2, result.size());
    }

    @Test
    void testGetUserOrders() {
        String userId = "abc123";
        List<BookingModel> bookingList = Arrays.asList(new BookingModel());
        Mockito.when(bookingRepository.findByUserId(userId)).thenReturn(bookingList);

        List<BookingModel> result = userController.getUserOrders(userId);

        Assertions.assertEquals(1, result.size());
    }
}
