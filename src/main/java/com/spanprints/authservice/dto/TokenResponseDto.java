package com.spanprints.authservice.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class TokenResponseDto {
	// Java 8 date/time type `java.time.Instant` not supported by default when using
	// `com.fasterxml.jackson.databind.ObjectMapper` for converting to JSON-String

//	private LocalDateTime timestamp;
	private String expiry;
	private String token;

	public TokenResponseDto(String token, LocalDateTime expirationTime) {
		this.expiry = expirationTime.toString();
		this.token = token;
	}
}
