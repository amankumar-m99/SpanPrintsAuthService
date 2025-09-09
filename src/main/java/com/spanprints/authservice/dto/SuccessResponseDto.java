package com.spanprints.authservice.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class SuccessResponseDto {
	// Java 8 date/time type `java.time.Instant` not supported by default when using
	// `com.fasterxml.jackson.databind.ObjectMapper` for converting to JSON-String

//	private LocalDateTime timestamp;
	private String timestamp;
	private int status;
	private String message;

	public SuccessResponseDto(HttpStatus status, String message) {
		this.timestamp = LocalDateTime.now().toString();
		this.status = status.value();
		this.message = message;
	}

	// getters
	public String getTimestamp() {
		return timestamp;
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

}
