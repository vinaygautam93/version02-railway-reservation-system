package com.casestudy.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.casestudy.model.TrainModel;

@Repository
public interface TrainRepository extends JpaRepository<TrainModel, Long> {
	List<TrainModel> findByTrainFromAndTrainTo(String trainFrom, String trainTo);
	List<TrainModel> findByTrainName(String trainName);
	TrainModel findByTrainNo(Long trainNo);
	List<TrainModel> findByTrainFrom(String trainFrom);
	List<TrainModel> findByTrainTo(String trainTo);
}