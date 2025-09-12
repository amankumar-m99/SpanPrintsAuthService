package com.spanprints.authservice.exceptionhandler;

import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.spanprints.authservice.dto.ErrorResponseDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionsHandler {

	// Handle @Valid body validation errors
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponseDto> handleValidationErrors(MethodArgumentNotValidException ex,
			HttpServletRequest request) {
		List<String> errors = ex.getBindingResult().getFieldErrors().stream()
				.map(err -> err.getField() + ": " + err.getDefaultMessage()).toList();

		ErrorResponseDto responseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST, null, "Validation failed",
				request.getRequestURI(), errors);
		return ResponseEntity.status(responseDto.getStatus()).body(responseDto);
	}

	// Handle validation errors for query params / path variables
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponseDto> handleConstraintViolation(ConstraintViolationException ex,
			HttpServletRequest request) {
		List<String> errors = ex.getConstraintViolations().stream()
				.map(violation -> violation.getPropertyPath() + ": " + violation.getMessage()).toList();
		ErrorResponseDto responseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST, null, "Validation failed",
				request.getRequestURI(), errors);
		return ResponseEntity.status(responseDto.getStatus()).body(responseDto);
	}

	@ExceptionHandler(HandlerMethodValidationException.class)
	public ResponseEntity<ErrorResponseDto> handleValidationExceptions(HandlerMethodValidationException ex,
			HttpServletRequest request) {
		String message = ex.getAllErrors().stream().map(err -> err.getDefaultMessage()).findFirst()
				.orElse("Validation failed");
		ErrorResponseDto responseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST, "Bad Request", message,
				request.getRequestURI(), null);
		return ResponseEntity.status(responseDto.getStatus()).body(responseDto);
	}

	// path-variable data-type mismatch
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponseDto> handleTypeMismatch(MethodArgumentTypeMismatchException ex,
			HttpServletRequest request) {
		String message = String.format("Invalid value '%s' for parameter '%s'. Expected type: %s", ex.getValue(),
				ex.getName(), ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown");
		ErrorResponseDto responseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST, "Bad Request", message,
				request.getRequestURI(), null);
		return ResponseEntity.status(responseDto.getStatus()).body(responseDto);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorResponseDto> handleHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
		ErrorResponseDto responseDto = new ErrorResponseDto(HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed",
				ex.getMessage(), request.getRequestURI(), null);
		return ResponseEntity.status(responseDto.getStatus()).body(responseDto);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex,
			HttpServletRequest request) {
		String message = ex.getMessage();
		if (message.contains("Required request body is missing")) {
			message = "Required request body is missing";
		}
		ErrorResponseDto responseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST, null, message,
				request.getRequestURI(), null);
		return ResponseEntity.status(responseDto.getStatus()).body(responseDto);
	}

}
