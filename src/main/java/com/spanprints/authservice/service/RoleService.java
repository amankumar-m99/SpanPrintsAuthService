package com.spanprints.authservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.RoleSaveDto;
import com.spanprints.authservice.dto.RoleUpdateDto;
import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.entity.Role;
import com.spanprints.authservice.exception.role.RoleAlreadyExistsException;
import com.spanprints.authservice.exception.role.RoleNotFoundException;
import com.spanprints.authservice.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository repository;

	public Role addRole(RoleSaveDto roleDto) {
		roleDto.setRoleName(roleDto.getRoleName().toUpperCase());
		throwIfRoleAlreadyExists(roleDto.getRoleName());
		Role role = buildRole(roleDto);
		return repository.save(role);
	}

	public Role getRoleById(Long roleId) {
		return repository.findById(roleId)
				.orElseThrow(() -> new RoleNotFoundException(String.format("No role exists with id `%d`", roleId)));
	}

	public List<Role> getAllRoles() {
		return repository.findAll();
	}

	public Role updateRole(RoleUpdateDto roleDto) {
		roleDto.setRoleName(roleDto.getRoleName().toUpperCase());
		Role role = getRoleById(roleDto.getRoleId());
		role = updateRoleDetails(role.getId(), roleDto);
		return repository.save(role);
	}

	public void deleteRole(Role role) {
		repository.delete(role);
	}

	public SuccessResponseDto deleteRoleById(Long roleId) {
		repository.delete(getRoleById(roleId));
		return new SuccessResponseDto(HttpStatus.OK, String.format("Deleted role by id `%d`", roleId));
	}

	public SuccessResponseDto deleteAllRoles() {
		repository.deleteAll();
		return new SuccessResponseDto(HttpStatus.OK, "Deleted all roles.");
	}

	private void throwIfRoleAlreadyExists(String roleName) {
		if (repository.findByRoleName(roleName).isPresent()) {
			throw new RoleAlreadyExistsException(String.format("One role already exists with name `%s`", roleName));
		}
	}

	private Role buildRole(RoleSaveDto roleSaveDto) {
		Role role = new Role(null, roleSaveDto.getRoleName(), roleSaveDto.getRoleDescription());
		return role;
	}

	private Role updateRoleDetails(Long id, RoleUpdateDto roleDto) {
		return new Role(id, roleDto.getRoleName(), roleDto.getRoleDescription());
	}

}
