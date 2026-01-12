package com.spanprints.authservice.exceptionhandler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spanprints.authservice.dto.ErrorResponseDto;
import com.spanprints.authservice.exception.printjob.PrintJobNotFoundException;
import com.spanprints.authservice.exception.printjob.PrintJobTypeAlreadyExistsException;
import com.spanprints.authservice.exception.printjob.PrintJobTypeNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PrintJobExceptionsHandler {

	@ExceptionHandler(PrintJobNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handlePrintJobNotFoundException(PrintJobNotFoundException ex,
			HttpServletRequest request) {
		ErrorResponseDto responseDto = new ErrorResponseDto(HttpStatus.NOT_FOUND, null, ex.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(responseDto.getStatus()).body(responseDto);
	}

	@ExceptionHandler(PrintJobTypeNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handlePrintJobTypeNotFoundException(PrintJobTypeNotFoundException ex,
			HttpServletRequest request) {
		ErrorResponseDto responseDto = new ErrorResponseDto(HttpStatus.NOT_FOUND, null, ex.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(responseDto.getStatus()).body(responseDto);
	}

	@ExceptionHandler(PrintJobTypeAlreadyExistsException.class)
	public ResponseEntity<ErrorResponseDto> handlePrintJobTypeAlreadyExistsException(
			PrintJobTypeAlreadyExistsException ex, HttpServletRequest request) {
		ErrorResponseDto responseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST, null, ex.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(responseDto.getStatus()).body(responseDto);
	}

}
