package com.te.homedeliveryapplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.te.homedeliveryapplication.entity.User;

@Repository
public interface DeliveryUserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);
}
