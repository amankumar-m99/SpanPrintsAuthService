package com.spanprints.authservice.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {
	// Java 8 date/time type `java.time.Instant` not supported by default when using
	// `com.fasterxml.jackson.databind.ObjectMapper` for converting to JSON-String

//	private LocalDateTime timestamp;
	private String timestamp;
	private int status;
	private String error;
	private String message;
	private String path;
	private List<String> fieldErrors; // optional

	public ErrorResponseDto(HttpStatus status, String error, String message, String path) {
		this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));
		this.status = status.value();
		this.error = (error == null || error.isBlank())?status.getReasonPhrase():error;
		this.message = message;
		this.path = path;
	}

	public ErrorResponseDto(HttpStatus status, String error, String message, String path, List<String> fieldErrors) {
		this(status, error, message, path);
		this.fieldErrors = fieldErrors;
	}
}
