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
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.Role;
import com.spanprints.authservice.entity.VerificationToken;
import com.spanprints.authservice.jwt.CustomUserDetailsService;
import com.spanprints.authservice.jwt.JwtResponseDto;
import com.spanprints.authservice.jwt.JwtUtils;
import com.spanprints.authservice.service.AccountService;
import com.spanprints.authservice.service.RoleService;
import com.spanprints.authservice.service.VerificationTokenService;

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

	private VerificationTokenService verificationTokenService;

	private RoleService roleService;

	public AuthController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService,
			JwtUtils jwtUtils, AccountService accountService, RoleService roleService,
			VerificationTokenService verificationTokenService) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtUtils = jwtUtils;
		this.accountService = accountService;
		this.roleService = roleService;
		this.verificationTokenService = verificationTokenService;
	}

	@GetMapping("/tokens")
	public ResponseEntity<List<VerificationToken>> getAllTokens() {
		List<VerificationToken> allTokens = verificationTokenService.getAllTokens();
		return new ResponseEntity<>(allTokens, HttpStatus.OK);
	}

	@DeleteMapping("/token/{tokenId}")
	public ResponseEntity<SuccessResponseDto> deleteTokenById(
			@PathVariable("tokenId") @NotNull @Positive @Min(1) Long id) {
		SuccessResponseDto response = verificationTokenService.deleteTokenById(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/verify")
	public ResponseEntity<SuccessResponseDto> verifyUser(@RequestParam("token") String verificationToken) {
		Account account = verificationTokenService.verifyToken(verificationToken);
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
	public ResponseEntity<SuccessResponseDto> register(@Valid @RequestBody RegisterRequestDto request) {
		String email = accountService.register(request);
		String message = String.format(
				"Account created sucessfully. Verification link sent to your registered e-mail address `%s`", email);
		SuccessResponseDto responseDto = new SuccessResponseDto(HttpStatus.CREATED, message);
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

	@GetMapping("/account/{accountId}")
	public ResponseEntity<Account> getAccountById(@PathVariable("accountId") @NotNull @Positive @Min(1) Long id) {
		Account accounts = accountService.getAccountById(id);
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}

	@GetMapping("/accounts")
	public ResponseEntity<List<Account>> getAllAccounts() {
		List<Account> accounts = accountService.getAllAccounts();
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}

	@PutMapping("/account")
	public ResponseEntity<String> updateAccount(@Valid @RequestBody RegisterRequestDto request) {
		return new ResponseEntity<>(accountService.register(request), HttpStatus.OK);
	}

	@PutMapping("/account-admin")
	public ResponseEntity<String> updateAccountByAdmin(@Valid @RequestBody RegisterRequestDto request) {
		return new ResponseEntity<>(accountService.register(request), HttpStatus.OK);
	}

	@DeleteMapping("/accounts")
	public ResponseEntity<SuccessResponseDto> deleteAllAccounts() {
		SuccessResponseDto response = accountService.deleteAllAccounts();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/account/{accountId}")
	public ResponseEntity<SuccessResponseDto> deleteAccountById(
			@PathVariable("accountId") @NotNull @Positive @Min(1) Long id) {
		SuccessResponseDto response = accountService.deleteById(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// ROLES

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
		return new ResponseEntity<>(roleService.deleteRoleById(id), HttpStatus.OK);
	}

	@DeleteMapping("/roles")
	public ResponseEntity<SuccessResponseDto> deleteAllRoles() {
		return new ResponseEntity<>(roleService.deleteAllRoles(), HttpStatus.OK);
	}

}
