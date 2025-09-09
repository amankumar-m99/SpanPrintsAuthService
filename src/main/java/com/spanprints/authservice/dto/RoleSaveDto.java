package com.spanprints.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleSaveDto {

	@NotBlank(message = "Role name is required")
	private String roleName;

	@NotBlank(message = "Role description is required")
	private String roleDescription;

}
