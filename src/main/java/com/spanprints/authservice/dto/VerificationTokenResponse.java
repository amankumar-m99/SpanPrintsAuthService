package com.spanprints.authservice.dto;

import java.time.format.DateTimeFormatter;

import com.spanprints.authservice.entity.VerificationToken;

import lombok.Getter;

@Getter
public class VerificationTokenResponse extends EntityResponseDto {
	// Java 8 date/time type `java.time.Instant` not supported by default when using
	// `com.fasterxml.jackson.databind.ObjectMapper` for converting to JSON-String

//	private LocalDateTime timestamp;
	private String expiry;
	private String token;
	private Boolean isUsed;
	private Long accountId;

	public VerificationTokenResponse(VerificationToken verificationToken) {
		super(verificationToken);
		this.expiry = verificationToken.getExpiryDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));
		this.token = verificationToken.getToken();
		this.isUsed = verificationToken.getIsUsed();
		this.accountId = verificationToken.getAccount() != null ? verificationToken.getAccount().getId() : null;
	}
}
