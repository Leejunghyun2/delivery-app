package com.example.deliveryapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import com.example.deliveryapp.support.TestcontainersConfig;

@SpringBootTest
@Import(TestcontainersConfig.class)
class DeliveryappApplicationTests {

	@Test
	void contextLoads() {
	}

}
