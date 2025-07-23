package com.casestudy.controller;

import com.casestudy.model.TrainModel;
import com.casestudy.service.TrainService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TrainControllerTest {

    @InjectMocks
    private TrainController trainController;

    @Mock
    private TrainService trainService;

    private TrainModel train;

    @BeforeEach
    void setUp() {
        train = new TrainModel();
        train.setTrainNo(123L);
        train.setTrainName("Express");
        train.setSeats(100);
        train.setFare(500);
    }

    @Test
    void testAddTrain() {
        ResponseEntity<String> response = trainController.addTrain(train);
        assertEquals("Train added successfully", response.getBody());
    }

    @Test
    void testGetAllTrains() {
        Mockito.when(trainService.getAllTrains()).thenReturn(Arrays.asList(train));
        List<TrainModel> result = trainController.getAllTrains();
        assertEquals(1, result.size());
    }

    @Test
    void testGetTrainByNo() {
        Mockito.when(trainService.getTrainByNo(123L)).thenReturn(train);
        TrainModel result = trainController.getTrainByNo(123L);
        assertEquals(train, result);
    }

    @Test
    void testUpdateSeats() {
        Mockito.when(trainService.updateSeats(Mockito.eq(123L), Mockito.any(TrainModel.class)))
                .thenReturn(ResponseEntity.ok("Seats updated"));
        ResponseEntity<String> response = trainController.updateSeats(123L, train);
        assertEquals("Seats updated", response.getBody());
    }

    @Test
    void testFindByLocation() {
        Mockito.when(trainService.findByLocation("A", "B")).thenReturn(Arrays.asList(train));
        List<TrainModel> result = trainController.findByLocation("A", "B");
        assertEquals(1, result.size());
    }

    @Test
    void testFindFareByNo() {
        Mockito.when(trainService.findFareByNo(123L)).thenReturn(550);
        int fare = trainController.findFareByNo(123L);
        assertEquals(550, fare);
    }
}
