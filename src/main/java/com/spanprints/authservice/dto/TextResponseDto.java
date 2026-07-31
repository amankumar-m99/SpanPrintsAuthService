package com.spanprints.authservice.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;

@Getter
public abstract class TextResponseDto {

	// Java 8 date/time type `java.time.Instant` not supported by default when using
		// `com.fasterxml.jackson.databind.ObjectMapper` for converting to JSON-String

//		private LocalDateTime timestamp;
		protected String timestamp;
		@JsonIgnore
		protected HttpStatus status;
		protected int statusCode;
		protected String message;

		protected TextResponseDto(HttpStatus status, String message) {
			this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));
			this.status = status;
			this.statusCode = status.value();
			this.message = message;
		}
}
