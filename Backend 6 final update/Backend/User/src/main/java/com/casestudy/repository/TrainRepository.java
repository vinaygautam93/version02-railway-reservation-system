package com.casestudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.casestudy.model.TrainModel;

import java.util.Optional;

@Repository
public interface TrainRepository extends JpaRepository<TrainModel, String> {
	TrainModel findByTrainFromAndTrainTo(String trainFrom, String trainTo);
	TrainModel findByTrainName(String trainName);
	Optional<TrainModel> findByTrainNo(String trainNo);

}