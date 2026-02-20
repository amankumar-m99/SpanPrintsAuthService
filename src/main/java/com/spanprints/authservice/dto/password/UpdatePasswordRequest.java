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
public class UpdatePasswordRequest {

	@NotNull(message = "Current password is required")
	@NotBlank(message = "Current password is required")
	private String currentPassword;

	@NotNull(message = "New Password is required")
	@NotBlank(message = "New Password is required")
	private String newPassword;

	@NotNull(message = "Confirm password is required")
	@NotBlank(message = "Confirm password is required")
	private String confirmPassword;
}
