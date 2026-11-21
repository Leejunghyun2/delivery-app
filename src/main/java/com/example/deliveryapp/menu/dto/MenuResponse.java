package com.example.deliveryapp.menu.dto;

import java.time.LocalDateTime;

import com.example.deliveryapp.menu.domain.MenuItem;
import com.example.deliveryapp.menu.domain.MenuStatus;

public record MenuResponse(
	Long id,
	Long storeId,
	String storeName,
	String name,
	String description,
	int price,
	MenuStatus status,
	LocalDateTime createdAt
	
) {
	
	public static MenuResponse from(MenuItem menuItem) {
		return new MenuResponse(
			menuItem.getId(),
			menuItem.getStore().getId(),
			menuItem.getStore().getName(),
			menuItem.getName(),
			menuItem.getDescription(),
			menuItem.getPrice(),
			menuItem.getStatus(),
			menuItem.getCreatedAt()
		);
	}
}
