package com.example.deliveryapp.user.dto;

import java.time.LocalDateTime;

import com.example.deliveryapp.user.domain.Role;
import com.example.deliveryapp.user.domain.User;

public record UserResponse(
		Long id,
		String email,
		String name,
		String phone,
		Role role,
		LocalDateTime createdAt
) {
	public static UserResponse from(User user) {
		return new UserResponse(
				user.getId(),
				user.getEmail(),
				user.getName(),
				user.getPhone(),
				user.getRole(),
				user.getCreatedAt()
		);
	}
}
