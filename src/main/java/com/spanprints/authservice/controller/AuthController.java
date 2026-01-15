package com.spanprints.authservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spanprints.authservice.dto.LoginRequestDto;
import com.spanprints.authservice.dto.ProfileResponse;
import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.dto.account.AccountResponse;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.PersonalDetails;
import com.spanprints.authservice.jwt.CustomUserDetailsService;
import com.spanprints.authservice.jwt.JwtResponseDto;
import com.spanprints.authservice.jwt.JwtUtils;
import com.spanprints.authservice.service.AccountService;
import com.spanprints.authservice.service.PersonalDetailsService;
import com.spanprints.authservice.service.VerificationTokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("")
public class AuthController {

	private JwtUtils jwtUtils;

	private AuthenticationManager authenticationManager;

	private CustomUserDetailsService userDetailsService;

	private AccountService accountService;

	private PersonalDetailsService personalDetailsService;

	private VerificationTokenService verificationTokenService;

	public AuthController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService,
			JwtUtils jwtUtils, AccountService accountService, PersonalDetailsService personalDetailsService,
			VerificationTokenService verificationTokenService) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtUtils = jwtUtils;
		this.accountService = accountService;
		this.personalDetailsService = personalDetailsService;
		this.verificationTokenService = verificationTokenService;
	}

	@GetMapping("/verify")
	public ResponseEntity<SuccessResponseDto> verifyUser(@RequestParam("token") String verificationToken) {
		Account account = verificationTokenService.verifyToken(verificationToken);
		accountService.enableAccount(account);
		SuccessResponseDto responseDto = new SuccessResponseDto(HttpStatus.OK, "Account verified successfully.");
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

	@PostMapping("/login")
	public ResponseEntity<JwtResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
			UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
			return new ResponseEntity<>(jwtUtils.generateJwtResponseDto(user), HttpStatus.OK);
		} catch (DisabledException e) {
			throw new DisabledException("Account is disabled");
		} catch (LockedException e) {
			throw new LockedException("Account is locked!");
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect username/password");
		} catch (Exception e) {
			throw e;
		}
	}

	@GetMapping("/me")
	public ResponseEntity<ProfileResponse> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
		Account account = accountService.getAccountByUsername(userDetails.getUsername());
		ProfileResponse dto = new ProfileResponse();
		dto.setAccount(new AccountResponse(account));
		if (account.getPersonalDetails() != null) {
			dto.setPersonalDetails(personalDetailsService.getPersonalDetailsById(account.getPersonalDetails().getId()));
		}
		else {
			dto.setPersonalDetails(new PersonalDetails());
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

}
