package com.spanprints.authservice.exceptionhandler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spanprints.authservice.dto.ErrorResponseDto;
import com.spanprints.authservice.exception.verificationtoken.VerificationTokenAlreadyUsedException;
import com.spanprints.authservice.exception.verificationtoken.VerificationTokenExpiredException;
import com.spanprints.authservice.exception.verificationtoken.VerificationTokenNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class VerificationTokenExceptionsHandler {

	@ExceptionHandler(VerificationTokenAlreadyUsedException.class)
	public ResponseEntity<ErrorResponseDto> handleVerificationTokenAlreadyUsedException(
			VerificationTokenAlreadyUsedException ex, HttpServletRequest request) {
		ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.BAD_REQUEST, null, ex.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
	}

	@ExceptionHandler(VerificationTokenExpiredException.class)
	public ResponseEntity<ErrorResponseDto> handleVerificationTokenExpiredException(
			VerificationTokenExpiredException ex, HttpServletRequest request) {
		ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.BAD_REQUEST, null, ex.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
	}

	@ExceptionHandler(VerificationTokenNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handleVerificationTokenNotFoundException(
			VerificationTokenNotFoundException ex, HttpServletRequest request) {
		ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.BAD_REQUEST, null, ex.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
	}

}
