package com.example.deliveryapp.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.deliveryapp.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	boolean existsByEmail(String Email);
	
	Optional<User> findEByEmail(String email);
}
