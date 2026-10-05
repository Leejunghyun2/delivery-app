package com.example.deliveryapp.user.dto;

import com.example.deliveryapp.user.domain.Role;
import com.example.deliveryapp.user.domain.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserSignupRequest(

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    String email,

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8~20자여야 합니다.")
    String password,

    @NotBlank(message = "이름은 필수입니다.")
    @Size(max = 50, message = "이름은 50자 이하여야 합니다.")
    String name,

    @Pattern(regexp = "^01[0-9]-?\\d{3,4}-?\\d{4}$", message = "휴대폰 번호 형식이 올바르지 않습니다.")
    String phone,

    @NotNull(message = "회원 유형은 필수입니다.")
    Role role
) {
	public User toEntity(String encodedPassword) {
		return User.builder()
				.email(email)
				.password(encodedPassword)
				.name(name)
				.phone(phone)
				.role(role)
				.build();
	}
	
}