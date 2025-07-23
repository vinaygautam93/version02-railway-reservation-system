package com.casestudy.model;


import lombok.*;


import javax.persistence.*;
import javax.persistence.GenerationType;

@Entity
@Table(name = "booking")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String pnrId;
	private String userId;
	private String name;
	private String phnnumber;
	private String email;
	private String trainNo;
	private String trainName;
	private String trainFrom;
	private String trainTo;
	private String date;
	private String time;
	@Column(name = "total_seats")
	private int totalseats;

	private int fare;

	@Enumerated(EnumType.STRING)
	private BookingStatus status;
 // CONFIRMED / CANCELLED / PENDING



}