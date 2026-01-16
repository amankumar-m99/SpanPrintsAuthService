package com.spanprints.authservice.dto.role;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleSaveRequest {

	@NotBlank(message = "Role name is required")
	private String roleName;

	@NotBlank(message = "Role description is required")
	private String roleDescription;

}
