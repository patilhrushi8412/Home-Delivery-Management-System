package com.te.homedeliveryapplication.entity;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
public class DeliveryBoyDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int deliveryGuyId;

	private String deliveryGuyName;

	private String email;
	
	private String location;

	private boolean availability;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
	private LocalTime availableFrom;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
	private LocalTime availableTo;

	private int rating;
}
