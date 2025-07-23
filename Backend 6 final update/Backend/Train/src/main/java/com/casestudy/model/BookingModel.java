package com.casestudy.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "bookings")
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
	private String phoneNumber;
	private String email;
	private String trainNo;
	private String trainName;
	private String fromLocation; // Renamed 'from' to avoid SQL keyword conflict
	private String toLocation;   // Renamed 'to'
	private String date;
	private int totalSeats;
}