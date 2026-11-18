package com.example.deliveryapp.store.domain;

import com.example.deliveryapp.global.entity.BaseTimeEntity;
import com.example.deliveryapp.user.domain.User;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stores")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseTimeEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id", nullable = false)
	private User owner;
	
	@Column(nullable = false, length = 100)
	private String name;
	
	@Column(length = 20)
	private String phone;
	
	@Column(nullable = false)
	private String address;
	
	@Column(nullable = false)
	private int minOrderPrice;
	
	@Column(nullable = false)
	private int deliveryFee;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private  StoreStatus status;

	@Builder
	public Store(Long id, User owner, String name, String phone, String address, int minOrderPrice, int deliveryFee) {
		
		this.id = id;
		this.owner = owner;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.minOrderPrice = minOrderPrice;
		this.deliveryFee = deliveryFee;
		this.status = StoreStatus.OPEN;
		
	}
	
	
	
	
	
	
}
