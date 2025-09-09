package com.spanprints.authservice.dto;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequestDto {

	@Email(message = "Email must be valid")
	@NotBlank(message = "Email must not be blank")
	private String email;

	private String username;

	@NotBlank(message = "Name must not be blank")
	private String password;

	private Set<String> roles;

}
