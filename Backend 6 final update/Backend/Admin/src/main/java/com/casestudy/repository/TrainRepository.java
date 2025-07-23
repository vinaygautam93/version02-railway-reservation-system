package com.casestudy.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.casestudy.model.TrainModel;

@Repository
public interface TrainRepository extends JpaRepository<TrainModel, Long> {
	TrainModel findByTrainFromAndTrainTo(String trainFrom, String trainTo);
	TrainModel findByTrainName(String trainName);
	Optional<TrainModel> findByTrainNo(String trainNo);
}
