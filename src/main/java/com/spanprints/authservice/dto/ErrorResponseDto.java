package com.spanprints.authservice.dto;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseDto extends TextResponseDto {
	private String error;
	private String path;
	private List<String> fieldErrors; // optional

	public ErrorResponseDto(HttpStatus status, String error, String message, String path) {
		super(status, message);
		this.error = (error == null || error.isBlank())?status.getReasonPhrase():error;
		this.path = path;
	}

	public ErrorResponseDto(HttpStatus status, String error, String message, String path, List<String> fieldErrors) {
		this(status, error, message, path);
		this.fieldErrors = fieldErrors;
	}
}
