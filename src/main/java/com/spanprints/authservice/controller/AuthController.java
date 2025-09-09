package com.spanprints.authservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spanprints.authservice.dto.LoginRequestDto;
import com.spanprints.authservice.dto.RegisterRequestDto;
import com.spanprints.authservice.dto.RoleSaveDto;
import com.spanprints.authservice.dto.RoleUpdateDto;
import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.dto.TokenResponseDto;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.Role;
import com.spanprints.authservice.jwt.CustomUserDetailsService;
import com.spanprints.authservice.jwt.JwtResponseDto;
import com.spanprints.authservice.jwt.JwtUtils;
import com.spanprints.authservice.service.AccountService;
import com.spanprints.authservice.service.AccountVerificationService;
import com.spanprints.authservice.service.RoleService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("")
public class AuthController {

	private JwtUtils jwtUtils;

	private AuthenticationManager authenticationManager;

	private CustomUserDetailsService userDetailsService;

	private AccountService accountService;

	private AccountVerificationService accountVerificationService;

	private RoleService roleService;

	public AuthController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService,
			JwtUtils jwtUtils, AccountService accountService, RoleService roleService,
			AccountVerificationService accountVerificationService) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtUtils = jwtUtils;
		this.accountService = accountService;
		this.roleService = roleService;
		this.accountVerificationService = accountVerificationService;
	}

	@GetMapping("/verify")
	public ResponseEntity<SuccessResponseDto> verifyUser(@RequestParam("token") String verificationToken) {
		Account account = accountVerificationService.verifyToken(verificationToken);
		accountService.enableAccount(account);
		HttpStatus status = HttpStatus.OK;
		SuccessResponseDto responseDto = new SuccessResponseDto(status, "Account verified successfully.");
		return new ResponseEntity<>(responseDto, status);
	}

	@PostMapping("/login")
	public ResponseEntity<JwtResponseDto> login(@RequestBody LoginRequestDto request) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
			UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
			return new ResponseEntity<>(jwtUtils.generateJwtResponseDto(user), HttpStatus.OK);
		} catch (DisabledException e) {
			throw new DisabledException("User is disabled");
		} catch (LockedException e) {
			throw new LockedException("Account is locked!");
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect username/password");
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/account")
	public ResponseEntity<TokenResponseDto> register(@Valid @RequestBody RegisterRequestDto request) {
		Account account = accountService.register(request);
		TokenResponseDto tokenResponseForAccount = accountVerificationService.getTokenResponseForAccount(account);
//		String link = "https://yourapp.com/api/auth/verify?token=" + token;
//		emailService.send(user.getEmail(), "Click to verify: " + link);
		return new ResponseEntity<>(tokenResponseForAccount, HttpStatus.CREATED);
	}

	@GetMapping("/account/{id}")
	public ResponseEntity<Account> getAccountById(@PathVariable @NotNull @Positive @Min(1) Long id) {
		Account accounts = accountService.getAccountById(id);
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}

	@GetMapping("/accounts")
	public ResponseEntity<List<Account>> getAllAccounts() {
		List<Account> accounts = accountService.getAllAccounts();
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}

	@PutMapping("/account")
	public ResponseEntity<Account> updateAccount(@Valid @RequestBody RegisterRequestDto request) {
		Account account = accountService.register(request);
		return new ResponseEntity<>(account, HttpStatus.OK);
	}

	@PutMapping("/account-admin")
	public ResponseEntity<Account> updateAccountByAdmin(@Valid @RequestBody RegisterRequestDto request) {
		Account account = accountService.register(request);
		return new ResponseEntity<>(account, HttpStatus.OK);
	}

	@DeleteMapping("/accounts")
	public ResponseEntity<SuccessResponseDto> deleteAllAccounts() {
		SuccessResponseDto response = accountService.deleteAllAccounts();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// ROLES

	@PostMapping("/role")
	public ResponseEntity<Role> addRole(@Valid @RequestBody RoleSaveDto role) {
		return new ResponseEntity<>(roleService.addRole(role), HttpStatus.CREATED);
	}

	@GetMapping("/role/{roleId}")
	public ResponseEntity<Role> getRole(@PathVariable @NotNull @Positive @Min(1) Long id) {
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
	public ResponseEntity<SuccessResponseDto> deleteRole(@PathVariable @NotNull @Positive @Min(1) Long roleId) {
		return new ResponseEntity<>(roleService.deleteRoleById(roleId), HttpStatus.OK);
	}

	@DeleteMapping("/roles")
	public ResponseEntity<SuccessResponseDto> deleteAllRoles() {
		return new ResponseEntity<>(roleService.deleteAllRoles(), HttpStatus.OK);
	}

}
