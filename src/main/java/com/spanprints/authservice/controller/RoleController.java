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

import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.dto.role.RoleResponse;
import com.spanprints.authservice.dto.role.RoleSaveRequest;
import com.spanprints.authservice.dto.role.RoleUpdateRequest;
import com.spanprints.authservice.service.RoleService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@PostMapping
	public ResponseEntity<RoleResponse> createRole(@Valid @RequestBody RoleSaveRequest request) {
		return new ResponseEntity<>(new RoleResponse(roleService.addRole(request)), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<RoleResponse>> getAllRoles() {
		List<RoleResponse> list = roleService.getAllRoles().stream().map(RoleResponse::new).toList();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<RoleResponse> getRole(@PathVariable @NotNull @Positive @Min(1) Long id) {
		RoleResponse roleResponse = new RoleResponse(roleService.getRoleById(id));
		return new ResponseEntity<>(roleResponse, HttpStatus.OK);
	}

	@PutMapping("/id/{id}")
	public ResponseEntity<RoleResponse> updateRole(@PathVariable @NotNull @Positive @Min(1) Long id,
			@Valid @RequestBody RoleUpdateRequest request) {
		RoleResponse roleResponse = new RoleResponse(roleService.updateRole(id, request));
		return new ResponseEntity<>(roleResponse, HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<SuccessResponseDto> deleteAllRoles() {
		SuccessResponseDto responseDto = roleService.deleteAllRoles();
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

	@DeleteMapping("/id/{id}")
	public ResponseEntity<SuccessResponseDto> deleteRole(@PathVariable @NotNull @Positive @Min(1) Long id) {
		SuccessResponseDto responseDto = roleService.deleteRoleById(id);
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}
}
