package com.spanprints.authservice.exceptionhandler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spanprints.authservice.dto.ErrorResponseDto;
import com.spanprints.authservice.exception.account.AccountNotFoundException;
import com.spanprints.authservice.exception.account.EmailAlreadyExistsException;
import com.spanprints.authservice.exception.account.UsernameAlreadyExistsException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AccountExceptionHandler {

	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handleAccountNotFoundException(AccountNotFoundException ex,
			HttpServletRequest request) {
		ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.NOT_FOUND, null, ex.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
	}

	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<ErrorResponseDto> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex,
			HttpServletRequest request) {
		ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.BAD_REQUEST, null, ex.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
	}

	@ExceptionHandler(UsernameAlreadyExistsException.class)
	public ResponseEntity<ErrorResponseDto> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex,
			HttpServletRequest request) {
		ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.BAD_REQUEST, null, ex.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
	}

}
