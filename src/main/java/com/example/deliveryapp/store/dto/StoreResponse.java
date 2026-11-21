package com.example.deliveryapp.store.dto;

import java.time.LocalDateTime;

import com.example.deliveryapp.store.domain.Store;
import com.example.deliveryapp.store.domain.StoreStatus;

public record StoreResponse(
		
	Long id,
    Long ownerId,
    String ownerName,
    String name,
    String phone,
    String address,
    int minOrderPrice,
    int deliveryFee,
    StoreStatus status,
    LocalDateTime createdAt
    
) {
	
	public static StoreResponse from(Store store) {
        return new StoreResponse(
                store.getId(),
                store.getOwner().getId(),
                store.getOwner().getName(),
                store.getName(),
                store.getPhone(),
                store.getAddress(),
                store.getMinOrderPrice(),
                store.getDeliveryFee(),
                store.getStatus(),
                store.getCreatedAt()
        );
    }
	
}
