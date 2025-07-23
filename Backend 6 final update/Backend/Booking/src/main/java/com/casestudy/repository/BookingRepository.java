package com.casestudy.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.casestudy.model.BookingModel;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Repository
public interface BookingRepository extends JpaRepository<BookingModel, Long> {
	@OneToMany(mappedBy = "booking", fetch = FetchType.LAZY)
	@Query("SELECT b FROM BookingModel b WHERE b.userId = :userId")
	List<BookingModel> findByUserId(@Param("userId") String userId);
	@Query("SELECT b FROM BookingModel b WHERE b.pnrId = :pnrId")
	BookingModel findByPnrId(@Param("pnrId") String pnrId);

	@Query(value = "SELECT * FROM booking ORDER BY id DESC LIMIT 1", nativeQuery = true)
	BookingModel findLatestBooking();

}
