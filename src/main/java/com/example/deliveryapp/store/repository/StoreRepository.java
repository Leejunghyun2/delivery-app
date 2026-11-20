package com.example.deliveryapp.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.deliveryapp.store.domain.Store;

public interface StoreRepository extends JpaRepository<Store, Long>{
	
	@Query("select s from Store s join fetch s.owner")
	List<Store> findAllWithOwner();
}
