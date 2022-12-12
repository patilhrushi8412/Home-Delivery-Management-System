package com.te.homedeliveryapplication.services;

import java.util.List;

import com.te.homedeliveryapplication.dto.AssignDeliveryDTO;
import com.te.homedeliveryapplication.dto.DeliveryBoyDto;
import com.te.homedeliveryapplication.dto.UserDto;
import com.te.homedeliveryapplication.entity.DeliveryBoyDetails;
import com.te.homedeliveryapplication.entity.User;

public interface HomeDeliveryService {

	User addUser(UserDto dto);

	DeliveryBoyDetails addDeliveryBoy(DeliveryBoyDto dto);

	AssignDeliveryDTO assginDeliveryBoy(AssignDeliveryDTO dto);

	User updateUser(UserDto dto1);

	DeliveryBoyDetails updateDeliveryBoy(DeliveryBoyDto dto1);

	User getUserDetail(String email);

	List<DeliveryBoyDetails> getAllDeliveryBoyDetails();

	List<User> getAllUsersDetail();

	DeliveryBoyDetails getDeliveryBoyDetail(String email);
}
