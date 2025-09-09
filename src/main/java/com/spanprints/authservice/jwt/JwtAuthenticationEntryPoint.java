package com.spanprints.authservice.jwt;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spanprints.authservice.dto.ErrorResponseDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException {
		HttpStatus status = HttpStatus.valueOf(HttpServletResponse.SC_UNAUTHORIZED);
		String message = null;
		if(request.getAttribute("jwt_error") != null) {
			message = request.getAttribute("jwt_error").toString();
		}
		if (message == null) {
			message = exception.getMessage();
		}
		
		String path = null;
		if(request.getAttribute("jwt_req_path") != null) {
			path = request.getAttribute("jwt_req_path").toString();
		}
		if(path == null) {
			path = request.getRequestURI();
		}
		response.setStatus(status.value());
		response.setContentType("application/json");
		ErrorResponseDto responseBody = new ErrorResponseDto(status, "Unauthorized", message, path);
		response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
        response.getWriter().flush();
	}
}
