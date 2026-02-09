package com.spanprints.authservice.exceptionhandler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spanprints.authservice.dto.ErrorResponseDto;
import com.spanprints.authservice.exception.inventory.InventoryItemAlreadyExistsException;
import com.spanprints.authservice.exception.inventory.InventoryItemNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class InventoryExceptionsHandler {

	@ExceptionHandler(InventoryItemNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handleInventoryItemNotFoundException(InventoryItemNotFoundException ex,
			HttpServletRequest request) {
		ErrorResponseDto responseDto = new ErrorResponseDto(HttpStatus.NOT_FOUND, null, ex.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(responseDto.getStatus()).body(responseDto);
	}

	@ExceptionHandler(InventoryItemAlreadyExistsException.class)
	public ResponseEntity<ErrorResponseDto> handleInventoryItemAlreadyExistsException(
			InventoryItemAlreadyExistsException ex, HttpServletRequest request) {
		ErrorResponseDto responseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST, null, ex.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(responseDto.getStatus()).body(responseDto);
	}

}
