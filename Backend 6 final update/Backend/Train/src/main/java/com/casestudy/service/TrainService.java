package com.casestudy.service;

import com.casestudy.model.TrainModel;
import com.casestudy.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TrainService {

    @Autowired
    private TrainRepository trainRepository;


    public TrainModel addTrain(TrainModel trainModel) {
        return trainRepository.save(trainModel);
    }

    public List<TrainModel> getAllTrains() {
        return trainRepository.findAll();
    }

    public TrainModel getTrainByNo(Long trainNo) {
        return trainRepository.findByTrainNo(trainNo);
    }

    public ResponseEntity<String> updateSeats(Long trainNo, TrainModel updatedTrain) {
        TrainModel train = trainRepository.findByTrainNo(trainNo);
        train.setSeats(updatedTrain.getSeats());
        trainRepository.save(train);
        return ResponseEntity.ok("Seats updated for train " + trainNo);
    }

    public List<TrainModel> getTrainsByName(String trainName) {
        return trainRepository.findByTrainName(trainName);
    }

    public String updateTrain(String trainNo, TrainModel trainModel) {
        trainRepository.save(trainModel);
        return "Train with no. " + trainNo + " has been updated successfully";
    }

    public void deleteTrain(Long trainNo) {
        TrainModel train = trainRepository.findByTrainNo(trainNo);
        trainRepository.delete(train);
    }

    public List<TrainModel> findByLocation(String trainFrom, String trainTo) {
        return trainRepository.findByTrainFromAndTrainTo(trainFrom, trainTo);
    }

    public List<TrainModel> findByFrom(String trainFrom) {
        return trainRepository.findByTrainFrom(trainFrom);
    }

    public List<TrainModel> findByTo(String trainTo) {
        return trainRepository.findByTrainTo(trainTo);
    }

    public int findFareByNo(Long trainNo) {
        return trainRepository.findByTrainNo(trainNo).getFare();
    }

    public void decreaseSeats(Long trainNo, int seats) {
        TrainModel train = trainRepository.findByTrainNo(trainNo);
        train.setSeats(train.getSeats() - seats);
        trainRepository.save(train);
    }

    public void increaseSeats(Long trainNo, int seats) {
        TrainModel train = trainRepository.findByTrainNo(trainNo);
        train.setSeats(train.getSeats() + seats);
        trainRepository.save(train);
    }

    public String findTimeByNo(Long trainNo) {
        return trainRepository.findByTrainNo(trainNo).getTime();
    }

    public String findTimeByName(Long trainName) {
        return trainRepository.findByTrainNo(trainName).getTime();
    }
}