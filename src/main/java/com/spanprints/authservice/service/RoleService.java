package com.spanprints.authservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.dto.role.RoleSaveRequest;
import com.spanprints.authservice.dto.role.RoleUpdateRequest;
import com.spanprints.authservice.entity.Role;
import com.spanprints.authservice.exception.role.RoleAlreadyExistsException;
import com.spanprints.authservice.exception.role.RoleNotFoundException;
import com.spanprints.authservice.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public Role addRole(RoleSaveRequest roleDto) {
		roleDto.setRoleName(roleDto.getRoleName().toUpperCase());
		throwIfRoleAlreadyExists(roleDto.getRoleName());
		Role role = buildRole(roleDto);
		return roleRepository.save(role);
	}

	public Role getRoleById(Long roleId) {
		return roleRepository.findById(roleId)
				.orElseThrow(() -> new RoleNotFoundException(String.format("No role exists with id `%d`", roleId)));
	}

	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	public Role updateRole(Long id, RoleUpdateRequest request) {
		request.setRoleName(request.getRoleName().toUpperCase());
		Role role = updateRoleDetails(id, request);
		return roleRepository.save(role);
	}

	public void deleteRole(Role role) {
		roleRepository.delete(role);
	}

	public SuccessResponseDto deleteRoleById(Long roleId) {
		roleRepository.delete(getRoleById(roleId));
		return new SuccessResponseDto(HttpStatus.OK, String.format("Deleted role by id `%d`", roleId));
	}

	public SuccessResponseDto deleteAllRoles() {
		roleRepository.deleteAll();
		return new SuccessResponseDto(HttpStatus.OK, "Deleted all roles.");
	}

	private void throwIfRoleAlreadyExists(String roleName) {
		if (roleRepository.findByRoleName(roleName).isPresent()) {
			throw new RoleAlreadyExistsException(String.format("One role already exists with name `%s`", roleName));
		}
	}

	private Role buildRole(RoleSaveRequest request) {
		Role role = new Role(request.getRoleName(), request.getRoleDescription());
		return role;
	}

	private Role updateRoleDetails(Long id, RoleUpdateRequest reuest) {
		Role role = getRoleById(id);
		role.setRoleName(reuest.getRoleName());
		role.setRoleDescription(reuest.getRoleDescription());
		return role;
	}

}
