package com.spanprints.authservice.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;

@Getter
public class SuccessResponseDto {
	// Java 8 date/time type `java.time.Instant` not supported by default when using
	// `com.fasterxml.jackson.databind.ObjectMapper` for converting to JSON-String

//	private LocalDateTime timestamp;
	private String timestamp;
	@JsonIgnore
	private HttpStatus status;
	private int statusCode;
	private String message;

	public SuccessResponseDto(HttpStatus status, String message) {
		this.timestamp = LocalDateTime.now().toString();
		this.statusCode = status.value();
		this.status = status;
		this.message = message;
	}
}
