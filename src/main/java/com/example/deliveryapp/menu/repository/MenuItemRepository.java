package com.example.deliveryapp.menu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.deliveryapp.menu.domain.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long>{
	
	List<MenuItem> findByStoreId(Long storeId);
	
}
