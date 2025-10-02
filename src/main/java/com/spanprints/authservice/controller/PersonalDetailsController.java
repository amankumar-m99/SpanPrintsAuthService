package com.spanprints.authservice.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spanprints.authservice.dto.PersonalDetailsDto;
import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.PersonalDetails;
import com.spanprints.authservice.service.AccountService;
import com.spanprints.authservice.service.PersonalDetailsService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("")
public class PersonalDetailsController {

	@Autowired
	private PersonalDetailsService personalDetailsService;

	@Autowired
	private AccountService accountService;

	@PostMapping("/personal-detail")
	public ResponseEntity<PersonalDetails> addRole(@Valid @RequestBody PersonalDetailsDto dto,
			@AuthenticationPrincipal UserDetails userDetails) {
		Account account = getAccount(userDetails);
		return new ResponseEntity<>(personalDetailsService.addPersonalDetail(dto, account), HttpStatus.CREATED);
	}

	@GetMapping("/personal-detail/{personalDetailsId}")
	public ResponseEntity<PersonalDetails> getRole(
			@PathVariable("personalDetailsId") @NotNull @Positive @Min(1) Long id) {
		return new ResponseEntity<>(personalDetailsService.getPersonalDetailsById(id), HttpStatus.OK);
	}

	@GetMapping("/personal-details")
	public ResponseEntity<List<PersonalDetails>> getAllDetails() {
		return new ResponseEntity<>(personalDetailsService.getAll(), HttpStatus.OK);
	}

	@PutMapping("/personal-detail")
	public ResponseEntity<PersonalDetails> updateRole(@Valid @RequestBody PersonalDetailsDto dto,
			@AuthenticationPrincipal UserDetails userDetails) {
		Account account = getAccount(userDetails);
		return new ResponseEntity<>(personalDetailsService.addPersonalDetail(dto, account), HttpStatus.CREATED);
	}

	@DeleteMapping("/personal-detail/{personalDetailsId}")
	public ResponseEntity<SuccessResponseDto> deletePersonalDetails(
			@PathVariable("personalDetailsId") @NotNull @Positive @Min(1) Long id) {
		SuccessResponseDto responseDto = personalDetailsService.deleteById(id);
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

	@DeleteMapping("/personal-details")
	public ResponseEntity<SuccessResponseDto> deleteAllRoles() {
		SuccessResponseDto responseDto = personalDetailsService.deleteAllDetails();
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

	private Account getAccount(UserDetails userDetails) {
		String username = userDetails.getUsername();
		return accountService.getAccountByUsername(username);
	}
}
