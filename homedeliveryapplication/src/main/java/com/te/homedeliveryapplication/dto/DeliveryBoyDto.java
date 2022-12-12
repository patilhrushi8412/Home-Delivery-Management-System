package com.te.homedeliveryapplication.dto;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class DeliveryBoyDto {

	private String deliveryGuyName;

	private String location;
	
	private String email;

	private boolean availability;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
	private LocalTime availableFrom;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
	private LocalTime availableTo;

	private int rating;

}
