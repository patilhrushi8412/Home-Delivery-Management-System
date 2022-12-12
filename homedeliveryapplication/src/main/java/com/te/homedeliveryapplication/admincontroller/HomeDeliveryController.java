package com.te.homedeliveryapplication.admincontroller;

import static com.te.homedeliveryapplication.constants.Constants.ADDING_DELIVERY_BOY;
import static com.te.homedeliveryapplication.constants.Constants.ADDING_USER;
import static com.te.homedeliveryapplication.constants.Constants.ASSIGNING_DELIVERY_BOY_TO_USER;
import static com.te.homedeliveryapplication.constants.Constants.DELIVERY_BOY_ADDED_SUCCESFULLY;
import static com.te.homedeliveryapplication.constants.Constants.DELIVERY_BOY_UPDATED_SUCCESFULLY;
import static com.te.homedeliveryapplication.constants.Constants.ENTER_CORRECT_EMAIL_ADDRESS;
import static com.te.homedeliveryapplication.constants.Constants.ENTER_DATA_CAREFULLY;
import static com.te.homedeliveryapplication.constants.Constants.GETTING_ALL_DELIVERY_BOY_DETAILS;
import static com.te.homedeliveryapplication.constants.Constants.GETTING_ALL_USER_DETAILS;
import static com.te.homedeliveryapplication.constants.Constants.GETTING_DELIVERY_BOY_DETAIL;
import static com.te.homedeliveryapplication.constants.Constants.GETTING_USER_DETAIL;
import static com.te.homedeliveryapplication.constants.Constants.UPDATING_DELIVERY_BOY_DETAILS;
import static com.te.homedeliveryapplication.constants.Constants.UPDATING_USER_DETAILS;
import static com.te.homedeliveryapplication.constants.Constants.USER_ADDED_SUCCESFULLY;
import static com.te.homedeliveryapplication.constants.Constants.USER_UPDATED_SUCCESFULLY;
import static com.te.homedeliveryapplication.constants.Constants.YOUR_DETAILS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.te.homedeliveryapplication.dto.AssignDeliveryDTO;
import com.te.homedeliveryapplication.dto.DeliveryBoyDto;
import com.te.homedeliveryapplication.dto.UserDto;
import com.te.homedeliveryapplication.entity.DeliveryBoyDetails;
import com.te.homedeliveryapplication.entity.User;
import com.te.homedeliveryapplication.responce.Responce;
import com.te.homedeliveryapplication.services.HomeDeliveryService;

@RestController
public class HomeDeliveryController {

	@Autowired
	HomeDeliveryService services;
	org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HomeDeliveryController.class);

	@PostMapping("/addUser")
	public ResponseEntity<Responce> addUser(@RequestBody UserDto dto) {
		log.info(ADDING_USER);
		log.warn(ENTER_DATA_CAREFULLY);
		User user = services.addUser(dto);
		return new ResponseEntity<Responce>(new Responce(false, USER_ADDED_SUCCESFULLY, user), HttpStatus.OK);
	}

	@PostMapping("/addDeliveryBoy")
	public ResponseEntity<Responce> addDeliveryBoy(@RequestBody DeliveryBoyDto dto) {
		log.info(ADDING_DELIVERY_BOY);
		log.warn(ENTER_DATA_CAREFULLY);
		DeliveryBoyDetails details = services.addDeliveryBoy(dto);
		return new ResponseEntity<Responce>(new Responce(false, DELIVERY_BOY_ADDED_SUCCESFULLY, details),
				HttpStatus.OK);
	}

	@PostMapping("/assignDeliveryBoy")
	public ResponseEntity<Responce> assignDeliveryBoy(@RequestBody AssignDeliveryDTO dto1) {
		log.info(ASSIGNING_DELIVERY_BOY_TO_USER);
		log.warn(ENTER_DATA_CAREFULLY);
		AssignDeliveryDTO dto = services.assginDeliveryBoy(dto1);
		return new ResponseEntity<Responce>(
				new Responce(false,
						"Delivery for the user : " + dto1.getUser().getUserName()
								+ " is assigned to the delivery person : "
								+ dto.getUser().getDeliveryBoy().getDeliveryGuyName() + " successfully",
						dto),
				HttpStatus.OK);
	}

	@PostMapping("/updateUser")
	public ResponseEntity<Responce> updateUser(@RequestBody UserDto dto1) {
		log.info(UPDATING_USER_DETAILS);
		log.warn(ENTER_DATA_CAREFULLY);
		User dto = services.updateUser(dto1);
		return new ResponseEntity<Responce>(new Responce(false, USER_UPDATED_SUCCESFULLY, dto), HttpStatus.OK);
	}

	@PostMapping("/updateDeliveryBoy")
	public ResponseEntity<Responce> updateDeliveryBoy(@RequestBody DeliveryBoyDto dto1) {
		log.info(UPDATING_DELIVERY_BOY_DETAILS);
		log.warn(ENTER_DATA_CAREFULLY);
		DeliveryBoyDetails dto = services.updateDeliveryBoy(dto1);
		return new ResponseEntity<Responce>(new Responce(false, DELIVERY_BOY_UPDATED_SUCCESFULLY, dto), HttpStatus.OK);
	}

	@GetMapping("/getUserDetail")
	public ResponseEntity<Responce> getUserDetail(@RequestParam String email) {
		log.info(GETTING_USER_DETAIL);
		log.warn(ENTER_CORRECT_EMAIL_ADDRESS);
		User user = services.getUserDetail(email);
		return new ResponseEntity<Responce>(new Responce(false, YOUR_DETAILS, user), HttpStatus.OK);
	}

	@GetMapping("/getDeliveryBoyDetail")
	public ResponseEntity<Responce> getDeliveryBoyDetail(@RequestParam String email) {
		log.info(GETTING_DELIVERY_BOY_DETAIL);
		log.warn(ENTER_CORRECT_EMAIL_ADDRESS);
		DeliveryBoyDetails deliveryBoy = services.getDeliveryBoyDetail(email);
		return new ResponseEntity<Responce>(new Responce(false, YOUR_DETAILS, deliveryBoy), HttpStatus.OK);
	}
	
	@GetMapping("/getAllDeliveryBoyDetails")
	public ResponseEntity<Responce> getAllDeliveryBoyDetails() {
		log.info(GETTING_ALL_DELIVERY_BOY_DETAILS);
		List<DeliveryBoyDetails> details=services.getAllDeliveryBoyDetails();
		return new ResponseEntity<Responce>(new Responce(false, YOUR_DETAILS, details), HttpStatus.OK);
	}
	
	@GetMapping("/getAllUsersDetails")
	public ResponseEntity<Responce> getAllUsersDetail() {
		log.info(GETTING_ALL_USER_DETAILS);
		List<User> details=services.getAllUsersDetail();
		return new ResponseEntity<Responce>(new Responce(false, YOUR_DETAILS, details), HttpStatus.OK);
	}
}
