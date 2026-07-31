package com.spanprints.authservice.dto;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessResponseDto extends TextResponseDto{

	public SuccessResponseDto(HttpStatus status, String message) {
		super(status, message);
	}
}
