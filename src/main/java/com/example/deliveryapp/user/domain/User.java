package com.example.deliveryapp.user.domain;

import com.example.deliveryapp.global.entity.BaseTimeEntity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 100)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String name;
	
	@Column(length = 20)
	private String phone;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Role role;

	@Builder
	public User(Long id, String email, String password, String name, String phone, Role role) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.role = role;
	}
	
}
