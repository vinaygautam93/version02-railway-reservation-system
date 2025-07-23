package com.casestudy.model;




import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "trains")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long trainNo;

	private String trainName;
	private String trainFrom;
	private String trainTo;
	private int fare;
	private int seats;
	private String time;
}