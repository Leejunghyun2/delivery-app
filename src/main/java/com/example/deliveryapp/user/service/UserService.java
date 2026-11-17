package com.example.deliveryapp.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.deliveryapp.global.exception.BusinessException;
import com.example.deliveryapp.global.exception.ErrorCode;
import com.example.deliveryapp.user.domain.User;
import com.example.deliveryapp.user.dto.UserResponse;
import com.example.deliveryapp.user.dto.UserSignupRequest;
import com.example.deliveryapp.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Transactional
	public UserResponse signup(UserSignupRequest request) {
		if(userRepository.existsByEmail(request.email())) {
			throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
		}
		
		User user = request.toEntity(passwordEncoder.encode(request.password()));
		User savedUser = userRepository.save(user);
		
		return UserResponse.from(savedUser);
	}
	
	public UserResponse findByid(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
		
		return UserResponse.from(user);
	}
	
	
}
