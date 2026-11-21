package com.example.deliveryapp.store.dto;

import com.example.deliveryapp.store.domain.Store;
import com.example.deliveryapp.user.domain.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record StoreCreateRequest(
		
	@NotNull(message = "사장님 ID는 필수입니다.")
	Long ownerId,
	
	@NotBlank(message = "가게 이름은 필수입니다.")
	@Size(max = 100, message = "가게 이름은 100자 이하여야 합니다.")
	String name,
	
	@Pattern(regexp = "^0\\d{1,2}-?\\d{3,4}-?\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
	String phone,
	
	@NotBlank(message = "주소는 필수입니다.")
	String address,
	
	@PositiveOrZero(message = "최소 주문 금액은 0원 이상이어야 합니다.")
	int minOrderPrice,
	
	@PositiveOrZero(message = "배달비는 0원 이상이어야 합니다.")
	int deliveryFee
) {
	public Store toEntity(User owner) {
		return Store.builder()
				.owner(owner)
				.name(name)
				.phone(phone)
				.address(address)
				.minOrderPrice(minOrderPrice)
				.deliveryFee(deliveryFee)
				.build();
						
	}
	
}
