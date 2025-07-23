package com.casestudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.casestudy.model.BookingModel;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingModel, Long> {
	List<BookingModel> findByUserId(String userId);
}