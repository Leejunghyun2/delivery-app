package com.example.deliveryapp.user.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.deliveryapp.user.dto.UserResponse;
import com.example.deliveryapp.user.dto.UserSignupRequest;
import com.example.deliveryapp.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	
	@PostMapping
	public ResponseEntity<UserResponse> signup(@Valid @RequestBody UserSignupRequest request){
		UserResponse response = userService.signup(request);
		return ResponseEntity
				.created(URI.create("/api/users/" + response.id()))
				.body(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> gerUser(@PathVariable Long id){
		return ResponseEntity.ok(userService.findByid(id));
	}
	
}
