package com.spanprints.authservice.dto.role;

import com.spanprints.authservice.dto.EntityResponseDto;
import com.spanprints.authservice.entity.Role;

import lombok.Getter;

@Getter
public class RoleResponse extends EntityResponseDto {

	private String roleName;
	private String roleDescription;

	public RoleResponse(Role role) {
		super(role);
		this.roleName = role.getRoleName();
		this.roleDescription = role.getRoleDescription();
	}

}
