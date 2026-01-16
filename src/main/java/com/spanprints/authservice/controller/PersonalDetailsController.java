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

import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.dto.personaldetails.CreatePersonalDetailsRequest;
import com.spanprints.authservice.dto.personaldetails.PersonalDetailsResponse;
import com.spanprints.authservice.dto.personaldetails.UpdatePersonalDetailsRequest;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.service.AccountService;
import com.spanprints.authservice.service.PersonalDetailsService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("personal-details")
public class PersonalDetailsController {

	@Autowired
	private PersonalDetailsService personalDetailsService;

	@Autowired
	private AccountService accountService;

	@PostMapping
	public ResponseEntity<PersonalDetailsResponse> createPersonalDetails(
			@Valid @RequestBody CreatePersonalDetailsRequest request,
			@AuthenticationPrincipal UserDetails userDetails) {
		Account account = accountService.getAccountByUserDetails(userDetails);
		PersonalDetailsResponse personalDetailsResponse = new PersonalDetailsResponse(
				personalDetailsService.createPersonalDetail(request, account));
		return new ResponseEntity<>(personalDetailsResponse, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<PersonalDetailsResponse>> getAllPersonalDetails() {
		List<PersonalDetailsResponse> list = personalDetailsService.getAll().stream().map(PersonalDetailsResponse::new)
				.toList();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<PersonalDetailsResponse> getPersonalDetailsById(
			@PathVariable @NotNull @Positive @Min(1) Long id) {
		PersonalDetailsResponse personalDetailsResponse = new PersonalDetailsResponse(
				personalDetailsService.getPersonalDetailsById(id));
		return new ResponseEntity<>(personalDetailsResponse, HttpStatus.OK);
	}

	@GetMapping("/uuid/{uuid}")
	public ResponseEntity<PersonalDetailsResponse> getPersonalDetailsByUuid(@PathVariable @NotNull String uuid) {
		PersonalDetailsResponse personalDetailsResponse = new PersonalDetailsResponse(
				personalDetailsService.getPersonalDetailsByUuid(uuid));
		return new ResponseEntity<>(personalDetailsResponse, HttpStatus.OK);
	}

	@PutMapping("/id/{id}")
	public ResponseEntity<PersonalDetailsResponse> updatePersonalDetailsById(
			@PathVariable @NotNull @Positive @Min(1) Long id, @Valid @RequestBody UpdatePersonalDetailsRequest request,
			@AuthenticationPrincipal UserDetails userDetails) {
		Account account = accountService.getAccountByUserDetails(userDetails);
		PersonalDetailsResponse personalDetailsResponse = new PersonalDetailsResponse(
				personalDetailsService.updatePersonalDetail(request, account));
		return new ResponseEntity<>(personalDetailsResponse, HttpStatus.CREATED);
	}

	@DeleteMapping
	public ResponseEntity<SuccessResponseDto> deleteAllPersonalDetails(
			@PathVariable("personalDetailsId") @NotNull @Positive @Min(1) Long id) {
		SuccessResponseDto responseDto = personalDetailsService.deleteAllDetails();
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

	@DeleteMapping("/id/{id}")
	public ResponseEntity<SuccessResponseDto> deletePersonalDetailsById(
			@PathVariable("personalDetailsId") @NotNull @Positive @Min(1) Long id) {
		SuccessResponseDto responseDto = personalDetailsService.deleteById(id);
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

	@DeleteMapping("/uuid/{uuid}")
	public ResponseEntity<SuccessResponseDto> deletePersonalDetailsByUuid(@PathVariable @NotNull String uuid) {
		SuccessResponseDto responseDto = personalDetailsService.deleteByUuid(uuid);
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}
}
