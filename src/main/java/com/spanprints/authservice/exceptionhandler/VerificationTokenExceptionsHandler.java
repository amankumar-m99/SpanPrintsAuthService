package com.spanprints.authservice.exceptionhandler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spanprints.authservice.dto.ErrorResponseDto;
import com.spanprints.authservice.exception.token.TokenAlreadyUsedException;
import com.spanprints.authservice.exception.token.TokenExpiredException;
import com.spanprints.authservice.exception.token.TokenNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class VerificationTokenExceptionsHandler {

	@ExceptionHandler(TokenAlreadyUsedException.class)
	public ResponseEntity<ErrorResponseDto> handleVerificationTokenAlreadyUsedException(
			TokenAlreadyUsedException ex, HttpServletRequest request) {
		ErrorResponseDto responseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST, null, ex.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(responseDto.getStatus()).body(responseDto);
	}

	@ExceptionHandler(TokenExpiredException.class)
	public ResponseEntity<ErrorResponseDto> handleVerificationTokenExpiredException(
			TokenExpiredException ex, HttpServletRequest request) {
		ErrorResponseDto responseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST, null, ex.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(responseDto.getStatus()).body(responseDto);
	}

	@ExceptionHandler(TokenNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handleVerificationTokenNotFoundException(
			TokenNotFoundException ex, HttpServletRequest request) {
		ErrorResponseDto responseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST, null, ex.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(responseDto.getStatus()).body(responseDto);
	}

}
