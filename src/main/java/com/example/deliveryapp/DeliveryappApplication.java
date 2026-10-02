package com.example.deliveryapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DeliveryappApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryappApplication.class, args);
	}

}
