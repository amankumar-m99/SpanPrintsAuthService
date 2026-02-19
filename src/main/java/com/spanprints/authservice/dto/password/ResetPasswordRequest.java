package com.spanprints.authservice.dto.password;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {

	@NotNull(message = "Token is required")
	@NotBlank(message = "Token is required")
	private String token;

	@NotNull(message = "Password is required")
	@NotBlank(message = "Password is required")
	private String password;

	@NotNull(message = "Confirm password is required")
	@NotBlank(message = "Confirm password is required")
	private String confirmPassword;
}
