package com.casestudy.model;

import javax.persistence.*;
import lombok.*;

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
	private String phnnumber;
	private String email;
	private String trainNo;
	private String trainName;
	private String trainFrom;
	private String trainTo;
	private String date;
	private String time;
	private int totalseats;
	private int fare;
}