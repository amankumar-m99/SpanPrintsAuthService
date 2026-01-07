package com.spanprints.authservice.dto.account;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest {

	@Email(message = "Email must be valid")
	@NotBlank(message = "Email must not be blank")
	private String email;

	@NotBlank(message = "Name must not be blank")
	private String username;

	private String password;

	private Set<String> roles;

}
