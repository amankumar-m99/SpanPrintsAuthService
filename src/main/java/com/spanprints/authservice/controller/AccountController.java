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
import com.spanprints.authservice.dto.account.CreateAccountRequest;
import com.spanprints.authservice.dto.account.UpdateAccountRequest;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.service.AccountService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PostMapping({ "/account", "/register" })
	public ResponseEntity<SuccessResponseDto> register(@Valid @RequestBody CreateAccountRequest request,
			HttpServletRequest httpServletRequest) {
		String whole = httpServletRequest.getRequestURL().toString();
		String frontendBaseUrl = whole.substring(0, whole.indexOf(httpServletRequest.getRequestURI()));
		String email = accountService.createAccount(request, frontendBaseUrl);
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
	public ResponseEntity<String> updateAccount(@Valid @RequestBody UpdateAccountRequest request) {
		return new ResponseEntity<>("Functionality not implemented yet.", HttpStatus.NOT_IMPLEMENTED);
	}

	@DeleteMapping("/accounts")
	public ResponseEntity<SuccessResponseDto> deleteAllAccounts() {
		SuccessResponseDto responseDto = accountService.deleteAllAccounts();
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

	@DeleteMapping("/account/{accountId}")
	public ResponseEntity<SuccessResponseDto> deleteAccountById(
			@PathVariable("accountId") @NotNull @Positive @Min(1) Long id) {
		SuccessResponseDto responseDto = accountService.deleteById(id);
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

}
