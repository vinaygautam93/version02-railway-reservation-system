package com.casestudy.service;

import com.casestudy.model.TrainModel;
import com.casestudy.repository.TrainRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainServiceTest {

    @InjectMocks
    private TrainService trainService;

    @Mock
    private TrainRepository trainRepository;

    private TrainModel train;

    @BeforeEach
    void setUp() {
        train = new TrainModel();
        train.setTrainNo(101L);
        train.setTrainName("Mumbai Express");
        train.setSeats(150);
        train.setFare(500);
        train.setTime("08:00 AM");
    }

    @Test
    void testAddTrain() {
        trainService.addTrain(train);
        verify(trainRepository, times(1)).save(train);
    }

    @Test
    void testGetAllTrains() {
        when(trainRepository.findAll()).thenReturn(Arrays.asList(train));
        List<TrainModel> trains = trainService.getAllTrains();
        assertEquals(1, trains.size());
    }

    @Test
    void testGetTrainByNo() {
        when(trainRepository.findByTrainNo(101L)).thenReturn(train);
        TrainModel result = trainService.getTrainByNo(101L);
        assertEquals("Mumbai Express", result.getTrainName());
    }

    @Test
    void testUpdateSeats() {
        TrainModel updated = new TrainModel();
        updated.setSeats(100);

        when(trainRepository.findByTrainNo(101L)).thenReturn(train);
        ResponseEntity<String> response = trainService.updateSeats(101L, updated);

        assertEquals("Seats updated for train 101", response.getBody());
        verify(trainRepository).save(train);
    }

    @Test
    void testDeleteTrain() {
        when(trainRepository.findByTrainNo(101L)).thenReturn(train);
        trainService.deleteTrain(101L);
        verify(trainRepository).delete(train);
    }

    @Test
    void testFindByLocation() {
        when(trainRepository.findByTrainFromAndTrainTo("A", "B")).thenReturn(Arrays.asList(train));
        List<TrainModel> result = trainService.findByLocation("A", "B");
        assertEquals(1, result.size());
    }

    @Test
    void testDecreaseSeats() {
        when(trainRepository.findByTrainNo(101L)).thenReturn(train);
        trainService.decreaseSeats(101L, 10);
        assertEquals(140, train.getSeats());
        verify(trainRepository).save(train);
    }

    @Test
    void testIncreaseSeats() {
        when(trainRepository.findByTrainNo(101L)).thenReturn(train);
        trainService.increaseSeats(101L, 5);
        assertEquals(155, train.getSeats());
        verify(trainRepository).save(train);
    }

    @Test
    void testFindFareByNo() {
        when(trainRepository.findByTrainNo(101L)).thenReturn(train);
        int fare = trainService.findFareByNo(101L);
        assertEquals(500, fare);
    }

    @Test
    void testFindTimeByNo() {
        when(trainRepository.findByTrainNo(101L)).thenReturn(train);
        String time = trainService.findTimeByNo(101L);
        assertEquals("08:00 AM", time);
    }


}