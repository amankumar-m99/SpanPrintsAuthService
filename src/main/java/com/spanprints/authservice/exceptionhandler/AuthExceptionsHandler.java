package com.spanprints.authservice.exceptionhandler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spanprints.authservice.dto.ErrorResponseDto;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthExceptionsHandler {

	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<ErrorResponseDto> handleDisabledException(DisabledException ex, HttpServletRequest request) {
		String message = ex.getMessage();
		ErrorResponseDto responseDto = new ErrorResponseDto(HttpStatus.UNAUTHORIZED, null, message,
				request.getRequestURI(), null);
		return ResponseEntity.status(responseDto.getStatus()).body(responseDto);
	}

	@ExceptionHandler(LockedException.class)
	public ResponseEntity<ErrorResponseDto> handleLockedException(LockedException ex, HttpServletRequest request) {
		String message = ex.getMessage();
		ErrorResponseDto responseDto = new ErrorResponseDto(HttpStatus.UNAUTHORIZED, null, message,
				request.getRequestURI(), null);
		return ResponseEntity.status(responseDto.getStatus()).body(responseDto);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponseDto> handleBadCredentialsException(BadCredentialsException ex,
			HttpServletRequest request) {
		String message = ex.getMessage();
		ErrorResponseDto responseDto = new ErrorResponseDto(HttpStatus.UNAUTHORIZED, null, message,
				request.getRequestURI(), null);
		return ResponseEntity.status(responseDto.getStatus()).body(responseDto);
	}
}
