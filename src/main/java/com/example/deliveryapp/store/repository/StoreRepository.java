package com.example.deliveryapp.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.deliveryapp.store.domain.Store;

public interface StoreRepository extends JpaRepository<Store, Long>{

	
}
