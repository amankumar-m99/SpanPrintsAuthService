package com.spanprints.authservice.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.spanprints.authservice.dto.role.RoleSaveRequest;
import com.spanprints.authservice.service.RoleService;

@Component
public class SeedingRunner implements CommandLineRunner {

	private RoleService roleService;

	public SeedingRunner(RoleService roleService) {
		this.roleService = roleService;
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			roleService.addRole(new RoleSaveRequest("user", "Regular user role"));
			roleService.addRole(new RoleSaveRequest("admin", "SuperAdmin role"));
		} catch (Exception e) {
		}
	}

}
