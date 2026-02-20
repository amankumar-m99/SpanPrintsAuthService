package com.spanprints.authservice.controller;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.dto.account.AccountResponse;
import com.spanprints.authservice.dto.account.CreateAccountRequest;
import com.spanprints.authservice.dto.account.UpdateAccountRequest;
import com.spanprints.authservice.dto.password.UpdatePasswordRequest;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.service.AccountService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PostMapping
	public ResponseEntity<SuccessResponseDto> createAccount(@Valid @RequestBody CreateAccountRequest request,
			@RequestHeader(value = "Origin", required = false) String frontendBaseUrl) {
		String email = accountService.createAccount(request, frontendBaseUrl);
		String message = String.format(
				"Account created sucessfully. Verification link sent to your registered e-mail address `%s`", email);
		SuccessResponseDto responseDto = new SuccessResponseDto(HttpStatus.CREATED, message);
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

	@GetMapping
	public ResponseEntity<List<AccountResponse>> getAllAccounts() {
		List<AccountResponse> list = accountService.getAllAccounts().stream().map(AccountResponse::new).toList();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<AccountResponse> getAccountById(@PathVariable @NotNull @Positive @Min(1) Long id) {
		AccountResponse accountResponse = new AccountResponse(accountService.getAccountById(id));
		return new ResponseEntity<>(accountResponse, HttpStatus.OK);
	}

	@GetMapping("/uuid/{uuid}")
	public ResponseEntity<AccountResponse> getAccountByUuid(@PathVariable @NotNull String uuid) {
		AccountResponse accountResponse = new AccountResponse(accountService.getAccountByUuid(uuid));
		return new ResponseEntity<>(accountResponse, HttpStatus.OK);
	}

	@PutMapping("/id/{id}")
	public ResponseEntity<String> updateAccountById(@PathVariable @NotNull @Positive @Min(1) Long id,
			@Valid @RequestBody UpdateAccountRequest request) {
		return new ResponseEntity<>("Functionality not implemented yet.", HttpStatus.NOT_IMPLEMENTED);
	}

	@PutMapping("/uuid/{uuid}")
	public ResponseEntity<String> updateAccountByUuid(@PathVariable @NotNull String uuid,
			@Valid @RequestBody UpdateAccountRequest request) {
		return new ResponseEntity<>("Functionality not implemented yet.", HttpStatus.NOT_IMPLEMENTED);
	}

	@PutMapping("/update-password")
	public ResponseEntity<Boolean> updatePassword(@Valid @RequestBody UpdatePasswordRequest request,
			@AuthenticationPrincipal UserDetails userDetails) throws BadRequestException {
		Account account = accountService.getAccountByUsername(userDetails.getUsername());
		accountService.updatePassword(account, request);
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<SuccessResponseDto> deleteAllAccounts() {
		SuccessResponseDto responseDto = accountService.deleteAllAccounts();
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

	@DeleteMapping("/id/{id}")
	public ResponseEntity<SuccessResponseDto> deleteAccountById(@PathVariable @NotNull @Positive @Min(1) Long id) {
		SuccessResponseDto responseDto = accountService.deleteById(id);
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

	@DeleteMapping("/uuid/{uuid}")
	public ResponseEntity<SuccessResponseDto> deleteAccountByUuidd(@PathVariable @NotNull String uuid) {
		SuccessResponseDto responseDto = accountService.deleteByUuid(uuid);
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

}
