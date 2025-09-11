package com.spanprints.authservice.exceptionhandler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spanprints.authservice.dto.ErrorResponseDto;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class FinalExceptionsHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto> handleAnyUnHandledException(Exception ex, HttpServletRequest request) {
		ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR,
				ex.getClass().toString(), ex.getMessage(), request.getRequestURI(), null);
		return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
	}
}
