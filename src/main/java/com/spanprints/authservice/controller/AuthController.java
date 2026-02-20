package com.spanprints.authservice.controller;

import java.util.Map;

import org.apache.coyote.BadRequestException;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.spanprints.authservice.dto.LoginRequestDto;
import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.dto.account.AccountResponse;
import com.spanprints.authservice.dto.account.CreateAccountRequest;
import com.spanprints.authservice.dto.password.ForgotPasswordRequest;
import com.spanprints.authservice.dto.password.ResetPasswordRequest;
import com.spanprints.authservice.dto.password.ResetPasswordTokenResponse;
import com.spanprints.authservice.dto.profile.ProfileResponse;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.PersonalDetails;
import com.spanprints.authservice.entity.ResetPasswordToken;
import com.spanprints.authservice.exception.account.AccountNotFoundException;
import com.spanprints.authservice.jwt.CustomUserDetailsService;
import com.spanprints.authservice.jwt.JwtResponseDto;
import com.spanprints.authservice.jwt.JwtUtils;
import com.spanprints.authservice.service.AccountService;
import com.spanprints.authservice.service.MailService;
import com.spanprints.authservice.service.PersonalDetailsService;
import com.spanprints.authservice.service.ResetPasswordTokenService;
import com.spanprints.authservice.service.VerificationTokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private JwtUtils jwtUtils;
	private AuthenticationManager authenticationManager;
	private CustomUserDetailsService userDetailsService;
	private AccountService accountService;
	private PersonalDetailsService personalDetailsService;
	private VerificationTokenService verificationTokenService;
	private ResetPasswordTokenService resetPasswordTokenService;
	private MailService mailService;

	public AuthController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService,
			JwtUtils jwtUtils, AccountService accountService, PersonalDetailsService personalDetailsService,
			VerificationTokenService verificationTokenService, ResetPasswordTokenService resetPasswordTokenService,
			MailService mailService) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtUtils = jwtUtils;
		this.accountService = accountService;
		this.personalDetailsService = personalDetailsService;
		this.verificationTokenService = verificationTokenService;
		this.resetPasswordTokenService = resetPasswordTokenService;
		this.mailService = mailService;
	}

	@PostMapping("/verify-account")
	public ResponseEntity<SuccessResponseDto> verifyUser(@RequestBody JsonNode node) {
//		node.get("user").get("name").asText();
		Account account = verificationTokenService.verifyToken(node.get("token").asText());
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

	@PostMapping("/register")
	public ResponseEntity<SuccessResponseDto> register(@Valid @RequestBody CreateAccountRequest request,
			@RequestHeader(value = "Origin", required = false) String frontendBaseUrl) {
		String email = accountService.createAccount(request, frontendBaseUrl);
		String message = String.format(
				"Account created sucessfully. Verification link sent to your registered e-mail address `%s`", email);
		SuccessResponseDto responseDto = new SuccessResponseDto(HttpStatus.CREATED, message);
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

	@GetMapping("/me")
	public ResponseEntity<ProfileResponse> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
		Account account = accountService.getAccountByUsername(userDetails.getUsername());
		ProfileResponse dto = new ProfileResponse();
		dto.setAccount(new AccountResponse(account));
		if (account.getPersonalDetails() != null) {
			dto.setPersonalDetails(personalDetailsService.getPersonalDetailsById(account.getPersonalDetails().getId()));
		} else {
			dto.setPersonalDetails(new PersonalDetails());
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<SuccessResponseDto> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request,
			@RequestHeader(value = "Origin", required = false) String frontendBaseUrl) {
		try {
			Account account = accountService.getAccountByEmail(request.getEmail());
			ResetPasswordTokenResponse tokenResponse = resetPasswordTokenService.getTokenResponseForAccount(account);
			mailService.sendResetPasswordMail(account.getEmail(), account.getUsername(), frontendBaseUrl,
					tokenResponse);
		} catch (AccountNotFoundException e) {
		}
		SuccessResponseDto dto = new SuccessResponseDto(HttpStatus.CREATED,
				"If this email is registered with us, a reset link has been sent.");
		return new ResponseEntity<>(dto, dto.getStatus());
	}

	@PostMapping("/verify-token-before-reset-password")
	public ResponseEntity<Boolean> verifyTokenBefore(@RequestBody Map<String, Object> body) {
		resetPasswordTokenService.verifyTokenBefore(body.get("token").toString());
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	@PostMapping("/reset-password")
	public ResponseEntity<Boolean> resetPassword(@Valid @RequestBody ResetPasswordRequest request)
			throws BadRequestException {
		ResetPasswordToken token = resetPasswordTokenService.verifyTokenBefore(request.getToken());
		if (accountService.updatePassword(token.getAccount(), request)) {
			resetPasswordTokenService.markTokenAsUsed(token);
		}
		return new ResponseEntity<>(true, HttpStatus.OK);
	}
}
