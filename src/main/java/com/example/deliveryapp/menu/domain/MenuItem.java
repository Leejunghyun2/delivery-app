package com.example.deliveryapp.menu.domain;

import com.example.deliveryapp.global.entity.BaseTimeEntity;
import com.example.deliveryapp.store.domain.Store;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "menu_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuItem extends BaseTimeEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id", nullable = false)
	private Store store;
	
	@Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MenuStatus status;
    
    @Builder
	public MenuItem(Long id, Store store, String name, String description, int price, MenuStatus status) {
		this.id = id;
		this.store = store;
		this.name = name;
		this.description = description;
		this.price = price;
		this.status = MenuStatus.ON_SALE;
	}
    
    public void update(String name, String description, int price) {
    	this.name = name;
    	this.description = description;
    	this.price = price;
    }
    
    public void soldOut() {
    	this.status = MenuStatus.SOLD_OUT;
    }
    
    public void resumeSale() {
    	this.status = MenuStatus.ON_SALE;
    }
	
}
