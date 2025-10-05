package com.spanprints.authservice.exceptionhandler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spanprints.authservice.dto.ErrorResponseDto;
import com.spanprints.authservice.exception.profilepic.ProfilePicFormatNotAcceptedException;
import com.spanprints.authservice.exception.profilepic.ProfilePicNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ProfilePicExceptionsHandler {

	@ExceptionHandler(ProfilePicNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handleProfilePicNotFoundException(ProfilePicNotFoundException ex,
			HttpServletRequest request) {
		ErrorResponseDto responseDto = new ErrorResponseDto(HttpStatus.NOT_FOUND, null, ex.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(responseDto.getStatus()).body(responseDto);
	}

	@ExceptionHandler(ProfilePicFormatNotAcceptedException.class)
	public ResponseEntity<ErrorResponseDto> handleProfilePicFormatNotAcceptedException(
			ProfilePicFormatNotAcceptedException ex, HttpServletRequest request) {
		ErrorResponseDto responseDto = new ErrorResponseDto(HttpStatus.NOT_FOUND, null, ex.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(responseDto.getStatus()).body(responseDto);
	}
}
