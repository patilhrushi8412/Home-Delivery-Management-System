package com.te.homedeliveryapplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.te.homedeliveryapplication.entity.DeliveryBoyDetails;

@Repository
public interface DeliveryGuyRepository extends JpaRepository<DeliveryBoyDetails, Integer> {

	Optional<DeliveryBoyDetails> findByDeliveryGuyName(String deliveryGuyName);

	Optional<DeliveryBoyDetails> findByEmail(String email);
}
