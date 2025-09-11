package com.spanprints.authservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spanprints.authservice.dto.RoleSaveDto;
import com.spanprints.authservice.dto.RoleUpdateDto;
import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.entity.Role;
import com.spanprints.authservice.service.RoleService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@PostMapping("/role")
	public ResponseEntity<Role> addRole(@Valid @RequestBody RoleSaveDto role) {
		return new ResponseEntity<>(roleService.addRole(role), HttpStatus.CREATED);
	}

	@GetMapping("/role/{roleId}")
	public ResponseEntity<Role> getRole(@PathVariable("roleId") @NotNull @Positive @Min(1) Long id) {
		return new ResponseEntity<>(roleService.getRoleById(id), HttpStatus.OK);
	}

	@GetMapping("/roles")
	public ResponseEntity<List<Role>> getAllRoles() {
		return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
	}

	@PutMapping("/role")
	public ResponseEntity<Role> updateRole(@Valid @RequestBody RoleUpdateDto role) {
		return new ResponseEntity<>(roleService.updateRole(role), HttpStatus.OK);
	}

	@DeleteMapping("/role/{roleId}")
	public ResponseEntity<SuccessResponseDto> deleteRole(@PathVariable("roleId") @NotNull @Positive @Min(1) Long id) {
		SuccessResponseDto responseDto = roleService.deleteRoleById(id);
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

	@DeleteMapping("/roles")
	public ResponseEntity<SuccessResponseDto> deleteAllRoles() {
		SuccessResponseDto responseDto = roleService.deleteAllRoles();
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}
}
