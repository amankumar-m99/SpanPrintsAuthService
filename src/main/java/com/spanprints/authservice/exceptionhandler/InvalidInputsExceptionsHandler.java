package com.spanprints.authservice.exceptionhandler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spanprints.authservice.dto.ErrorResponseDto;
import com.spanprints.authservice.exception.InvalidInputsException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class InvalidInputsExceptionsHandler {

	@ExceptionHandler(InvalidInputsException.class)
	public ResponseEntity<ErrorResponseDto> handleInvalidInputsException(InvalidInputsException ex,
			HttpServletRequest request) {
		ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.BAD_REQUEST, null, ex.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
	}
}
