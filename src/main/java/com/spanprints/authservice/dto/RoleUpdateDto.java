package com.spanprints.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleUpdateDto {

	@Positive
	private Long roleId;

	@NotBlank(message = "Role name is required")
	private String roleName;

	@NotBlank(message = "Role description is required")
	private String roleDescription;

}
