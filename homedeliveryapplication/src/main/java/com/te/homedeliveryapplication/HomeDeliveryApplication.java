package com.te.homedeliveryapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@OpenAPIDefinition /* localhost:9090/swagger-ui/index.html */
public class HomeDeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeDeliveryApplication.class, args);
	}
}
