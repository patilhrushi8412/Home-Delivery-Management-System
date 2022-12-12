package com.te.homedeliveryapplication.dto;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.te.homedeliveryapplication.entity.Address;
import com.te.homedeliveryapplication.entity.DeliveryBoyDetails;

import lombok.Data;

@Data
public class UserDto {

	private String userName;

	
	private String email;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
	private LocalTime reqiredFrom;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
	private LocalTime reqiredTo;

	private Address address;

	private DeliveryBoyDetails deliveryBoy;
}
