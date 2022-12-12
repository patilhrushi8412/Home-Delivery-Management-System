package com.te.homedeliveryapplication.services;

import static com.te.homedeliveryapplication.constants.Constants.DELIVERY_BOY_ASSIGNED_SUCCESFULLY;
import static com.te.homedeliveryapplication.constants.Constants.DELIVERY_BOY_DETAILS_NOT_INSERTED_CORRECTLY;
import static com.te.homedeliveryapplication.constants.Constants.DELIVERY_BOY_FOUND_SUCCESFULLY;
import static com.te.homedeliveryapplication.constants.Constants.DELIVERY_BOY_FOUND_SUCCESFULLY2;
import static com.te.homedeliveryapplication.constants.Constants.DELIVERY_BOY_NOT_FOUND;
import static com.te.homedeliveryapplication.constants.Constants.DELIVERY_BOY_NOT_PRESENT_WHICH_YOU_UPDATE;
import static com.te.homedeliveryapplication.constants.Constants.DELIVERY_BOY_SAVED_SUCCESFULLY;
import static com.te.homedeliveryapplication.constants.Constants.DELIVERY_BOY_UPDATED_SUCCESFULLY;
import static com.te.homedeliveryapplication.constants.Constants.DETAILS_OF_ALL_DELIVERY_BOYS;
import static com.te.homedeliveryapplication.constants.Constants.DETAILS_OF_ALL_USERS;
import static com.te.homedeliveryapplication.constants.Constants.INVALID_EMAIL_ADDRESS;
import static com.te.homedeliveryapplication.constants.Constants.NO_ANY_USER_REGISTERED;
import static com.te.homedeliveryapplication.constants.Constants.NO_DELIVERY_BOY_AVAILABLE;
import static com.te.homedeliveryapplication.constants.Constants.NO_DELIVERY_BOY_AVAILABLE_IN_COMPANY;
import static com.te.homedeliveryapplication.constants.Constants.SORRY_DELIVERY_BOY_IS_ALREADY_OCCUPIED_WITH_OTHER_DELIVERY_WE_ARE_UNABLE_TO_ASSIGN_DELIVERY_OF_YOUR_PRODUCT_TO_OTHER_DELIVERY_PERSONS_ALSO_RIGHT_NOW;
import static com.te.homedeliveryapplication.constants.Constants.SORRY_DELIVERY_BOY_IS_NOT_AVALIABLE_FOR_THIS_ADDRESS;
import static com.te.homedeliveryapplication.constants.Constants.USER_DATA_NOT_INSERTED_CORRECTLY;
import static com.te.homedeliveryapplication.constants.Constants.USER_FOUND_SUCCESFULLY;
import static com.te.homedeliveryapplication.constants.Constants.USER_NOT_FOUND_PLEASE_CREATE_ACCOUNT;
import static com.te.homedeliveryapplication.constants.Constants.USER_NOT_PRESENT_WHICH_YOU_UPDATE;
import static com.te.homedeliveryapplication.constants.Constants.USER_SAVED_SUCCESFULLY;
import static com.te.homedeliveryapplication.constants.Constants.USER_UPDATED_SUCCESFULLY;
import static com.te.homedeliveryapplication.constants.Constants.YOU_ALLREADY_HAVE_AN_ACCOUNT_ON_THIS_EMAIL_ID;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.te.homedeliveryapplication.customexception.CustomException;
import com.te.homedeliveryapplication.dto.AssignDeliveryDTO;
import com.te.homedeliveryapplication.dto.DeliveryBoyDto;
import com.te.homedeliveryapplication.dto.UserDto;
import com.te.homedeliveryapplication.entity.DeliveryBoyDetails;
import com.te.homedeliveryapplication.entity.User;
import com.te.homedeliveryapplication.repository.DeliveryGuyRepository;
import com.te.homedeliveryapplication.repository.DeliveryUserRepository;

@Service
public class HomeDeliveryServiceImpl implements HomeDeliveryService {

	@Autowired
	DeliveryUserRepository userRepo;

	@Autowired
	DeliveryGuyRepository guyRepo;

	org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HomeDeliveryServiceImpl.class);

	@Override
	public User addUser(UserDto dto) {
		try {
			Optional<User> findByEmail = userRepo.findByEmail(dto.getEmail());
			if (findByEmail.isPresent()) {
				throw new CustomException(YOU_ALLREADY_HAVE_AN_ACCOUNT_ON_THIS_EMAIL_ID);
			} else {
				User user = new User();
				BeanUtils.copyProperties(dto, user);
				User save = userRepo.save(user);
				if (save == null) {
					log.error(USER_DATA_NOT_INSERTED_CORRECTLY);
					throw new CustomException(USER_DATA_NOT_INSERTED_CORRECTLY);
				} else {
					log.info(USER_SAVED_SUCCESFULLY);
					return save;
				}
			}
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public DeliveryBoyDetails addDeliveryBoy(DeliveryBoyDto dto) {
		Optional<DeliveryBoyDetails> boy = guyRepo.findByEmail(dto.getEmail());
		if (boy.isPresent()) {
			throw new CustomException(YOU_ALLREADY_HAVE_AN_ACCOUNT_ON_THIS_EMAIL_ID);
		}
		DeliveryBoyDetails details = new DeliveryBoyDetails();
		try {
			BeanUtils.copyProperties(dto, details);
			
			DeliveryBoyDetails save = guyRepo.save(details);
			if (save == null) {
				log.error(DELIVERY_BOY_DETAILS_NOT_INSERTED_CORRECTLY);
				throw new CustomException(DELIVERY_BOY_DETAILS_NOT_INSERTED_CORRECTLY);
			} else {
				log.info(DELIVERY_BOY_SAVED_SUCCESFULLY);
				return save;
			}
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public AssignDeliveryDTO assginDeliveryBoy(AssignDeliveryDTO dto) {
		try {
			String email = dto.getUser().getEmail();
			Optional<User> user = userRepo.findByEmail(email);

			if (user == null) {
				log.error(USER_NOT_FOUND_PLEASE_CREATE_ACCOUNT);
				throw new CustomException(USER_NOT_FOUND_PLEASE_CREATE_ACCOUNT);
			}
			log.info(USER_FOUND_SUCCESFULLY);
			List<DeliveryBoyDetails> findAll = guyRepo.findAll();
			if (findAll.isEmpty()) {
				throw new CustomException(NO_DELIVERY_BOY_AVAILABLE);
			}
			List<DeliveryBoyDetails> collect = findAll.stream().filter(t -> {
				return t.getAvailableFrom().isAfter(user.get().getReqiredFrom())
						|| t.getAvailableTo().isBefore(user.get().getReqiredTo());
			}).collect(Collectors.toList());
			if (collect.isEmpty()) {
				log.error(
						SORRY_DELIVERY_BOY_IS_ALREADY_OCCUPIED_WITH_OTHER_DELIVERY_WE_ARE_UNABLE_TO_ASSIGN_DELIVERY_OF_YOUR_PRODUCT_TO_OTHER_DELIVERY_PERSONS_ALSO_RIGHT_NOW);
				throw new CustomException(
						SORRY_DELIVERY_BOY_IS_ALREADY_OCCUPIED_WITH_OTHER_DELIVERY_WE_ARE_UNABLE_TO_ASSIGN_DELIVERY_OF_YOUR_PRODUCT_TO_OTHER_DELIVERY_PERSONS_ALSO_RIGHT_NOW);
			} else {
				List<DeliveryBoyDetails> collect2 = collect.stream().filter(t -> {
					return t.getLocation().equals(user.get().getAddress().getAddress());
				}).collect(Collectors.toList());

				if (collect2.isEmpty()) {
					log.error(SORRY_DELIVERY_BOY_IS_NOT_AVALIABLE_FOR_THIS_ADDRESS);
					throw new CustomException(SORRY_DELIVERY_BOY_IS_NOT_AVALIABLE_FOR_THIS_ADDRESS);
				} else {
					DeliveryBoyDetails deliveryBoy = collect2.stream().max((a1, a2) -> a1.getRating() - a2.getRating())
							.get();
					user.get().setDeliveryBoy(deliveryBoy);
					deliveryBoy.setAvailableFrom(user.get().getReqiredTo());
					deliveryBoy.setAvailableTo(user.get().getReqiredFrom());
					deliveryBoy.setAvailability(false);
					userRepo.save(user.get());
					guyRepo.save(deliveryBoy);
					log.info(DELIVERY_BOY_ASSIGNED_SUCCESFULLY);
					dto.setUser(user.get());
					return dto;
				}
			}

		} catch (RuntimeException e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public User updateUser(UserDto dto1) {
		try {
			Optional<User> findByEmail = userRepo.findByEmail(dto1.getEmail());
			if (!findByEmail.isPresent()) {
				log.error(USER_NOT_PRESENT_WHICH_YOU_UPDATE);
				throw new CustomException(USER_NOT_PRESENT_WHICH_YOU_UPDATE);
			} else {
				log.info(USER_FOUND_SUCCESFULLY);
				BeanUtils.copyProperties(dto1, findByEmail.get());
				User save = userRepo.save(findByEmail.get());
				log.info(USER_UPDATED_SUCCESFULLY);
				return save;
			}
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public DeliveryBoyDetails updateDeliveryBoy(DeliveryBoyDto dto) {
		try {
			Optional<DeliveryBoyDetails> details = guyRepo.findByEmail(dto.getEmail());
			if (!details.isPresent()) {
				log.error(DELIVERY_BOY_NOT_PRESENT_WHICH_YOU_UPDATE);
				throw new CustomException(DELIVERY_BOY_NOT_PRESENT_WHICH_YOU_UPDATE);
			} else {
				log.info(DELIVERY_BOY_FOUND_SUCCESFULLY);
				BeanUtils.copyProperties(dto, details.get());
				DeliveryBoyDetails save = guyRepo.save(details.get());
				log.info(DELIVERY_BOY_UPDATED_SUCCESFULLY);
				return save;
			}
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public User getUserDetail(String email) {
		try {
			Optional<User> user = userRepo.findByEmail(email);
			if (!user.isPresent()) {
				log.error(INVALID_EMAIL_ADDRESS);
				throw new CustomException(INVALID_EMAIL_ADDRESS);
			} else {
				log.info(USER_FOUND_SUCCESFULLY);
				return user.get();
			}
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public List<DeliveryBoyDetails> getAllDeliveryBoyDetails() {
		try {
			List<DeliveryBoyDetails> findAll = guyRepo.findAll();
			if (findAll.isEmpty()) {
				log.error(NO_DELIVERY_BOY_AVAILABLE_IN_COMPANY);
				throw new CustomException(NO_DELIVERY_BOY_AVAILABLE_IN_COMPANY);
			} else {
				log.info(DETAILS_OF_ALL_DELIVERY_BOYS);
				return findAll;
			}
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public List<User> getAllUsersDetail() {
		try {
			List<User> userList = userRepo.findAll();
			if (userList.isEmpty()) {
				log.error(NO_ANY_USER_REGISTERED);
				throw new CustomException(NO_ANY_USER_REGISTERED);
			} else {
				log.info(DETAILS_OF_ALL_USERS);
				return userList;
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			throw e;
		}
	}

	@Override
	public DeliveryBoyDetails getDeliveryBoyDetail(String email) {
		try {
			Optional<DeliveryBoyDetails> boy = guyRepo.findByEmail(email);
			if (!boy.isPresent()) {
				log.error(DELIVERY_BOY_NOT_FOUND);
				throw new CustomException(DELIVERY_BOY_NOT_FOUND);
			} else {
				log.info(DELIVERY_BOY_FOUND_SUCCESFULLY2);
				return boy.get();
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			throw e;
		}
	}
}
