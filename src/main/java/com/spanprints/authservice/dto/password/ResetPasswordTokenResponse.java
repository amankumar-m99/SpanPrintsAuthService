package com.spanprints.authservice.dto.password;

import java.time.format.DateTimeFormatter;

import com.spanprints.authservice.dto.EntityResponseDto;
import com.spanprints.authservice.entity.ResetPasswordToken;

import lombok.Getter;

@Getter
public class ResetPasswordTokenResponse extends EntityResponseDto {
	// Java 8 date/time type `java.time.Instant` not supported by default when using
	// `com.fasterxml.jackson.databind.ObjectMapper` for converting to JSON-String

//	private LocalDateTime timestamp;
	private String expiry;
	private String token;
	private Boolean isUsed;
	private Long accountId;

	public ResetPasswordTokenResponse(ResetPasswordToken token) {
		super(token);
		this.expiry = token.getExpiryDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));
		this.token = token.getToken();
		this.isUsed = token.getIsUsed();
		this.accountId = token.getAccount() != null ? token.getAccount().getId() : null;
	}
}
