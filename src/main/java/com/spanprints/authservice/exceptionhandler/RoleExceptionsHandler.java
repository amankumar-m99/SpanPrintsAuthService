package com.spanprints.authservice.exceptionhandler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spanprints.authservice.dto.ErrorResponseDto;
import com.spanprints.authservice.exception.role.RoleAlreadyExistsException;
import com.spanprints.authservice.exception.role.RoleNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RoleExceptionsHandler {

	@ExceptionHandler(RoleNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handleRoleNotFoundException(RoleNotFoundException ex,
			HttpServletRequest request) {
		ErrorResponseDto responseDto = new ErrorResponseDto(HttpStatus.NOT_FOUND, null, ex.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(responseDto.getStatus()).body(responseDto);
	}

	@ExceptionHandler(RoleAlreadyExistsException.class)
	public ResponseEntity<ErrorResponseDto> handleRoleAlreadyExistsException(RoleAlreadyExistsException ex,
			HttpServletRequest request) {
		ErrorResponseDto responseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST, null, ex.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(responseDto.getStatus()).body(responseDto);
	}

}
