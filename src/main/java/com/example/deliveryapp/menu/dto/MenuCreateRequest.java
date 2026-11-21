package com.example.deliveryapp.menu.dto;

import com.example.deliveryapp.menu.domain.MenuItem;
import com.example.deliveryapp.store.domain.Store;

import jakarta.validation.constraints.*;

public record MenuCreateRequest(
	
	@NotBlank(message = "메뉴 이름은 필수입니다.")
	@Size(max = 100, message = "메뉴 이름은 100자 이하여야 합니다.")
	String name,
	
	@Size(max = 500, message = "메뉴 설명은 500자 이하여야 합니다.")
	String description,
	
	@Positive(message = "가겨은 1원 이상이어야 합니다.")
	int price
) {

	public MenuItem toEntity(Store store) {
		return MenuItem.builder()
				.store(store)
				.name(name)
				.description(description)
				.price(price)
				.build();
	}

}
