package com.spanprints.authservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.entity.VerificationToken;
import com.spanprints.authservice.service.VerificationTokenService;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("")
public class VerificationTokenController {

	@Autowired
	private VerificationTokenService verificationTokenService;

	@GetMapping("/tokens")
	public ResponseEntity<List<VerificationToken>> getAllTokens() {
		List<VerificationToken> allTokens = verificationTokenService.getAllTokens();
		return new ResponseEntity<>(allTokens, HttpStatus.OK);
	}

	@DeleteMapping("/token/{tokenId}")
	public ResponseEntity<SuccessResponseDto> deleteTokenById(
			@PathVariable("tokenId") @NotNull @Positive @Min(1) Long id) {
		SuccessResponseDto responseDto = verificationTokenService.deleteTokenById(id);
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}
}
