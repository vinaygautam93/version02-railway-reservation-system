package com.casestudy.model;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "trains")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainModel {

	@Id
	private String trainNo;
	private String trainName;
	private String trainFrom;
	private String trainTo;
	private int fare;
	private int seats;
	private String time;
}