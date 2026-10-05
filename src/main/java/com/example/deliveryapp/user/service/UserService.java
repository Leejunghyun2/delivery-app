package com.example.deliveryapp.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
			throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
		}
		
		User user = request.toEntity(passwordEncoder.encode(request.password()));
		User savedUser = userRepository.save(user);
		
		return UserResponse.from(savedUser);
	}
	
	public UserResponse findByid(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
		
		return UserResponse.from(user);
	}
	
	
}
