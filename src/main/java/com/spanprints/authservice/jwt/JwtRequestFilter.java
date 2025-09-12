package com.spanprints.authservice.jwt;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	private UserDetailsService userDetailsService;

	private JwtUtils jwtUtil;

	public JwtRequestFilter(UserDetailsService userDetailsService, JwtUtils jwtUtil) {
		this.userDetailsService = userDetailsService;
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorizationHeader = request.getHeader("Authorization");
		String username = null;
		String jwt = null;
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7);
			String reqAttributeName = "jwt_error";
			request.setAttribute("jwt_req_path", request.getRequestURI());
			try {
				username = jwtUtil.extractUsername(jwt);
				if (username == null) {
					throw new IllegalArgumentException("Bad Credentials. No username found.");
				}
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				if (jwtUtil.validateToken(jwt)) {
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,
							null, userDetails.getAuthorities());
					auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			} catch (ExpiredJwtException e) {
				request.setAttribute(reqAttributeName, "JWT expired");
				throw new BadCredentialsException("JWT expired", e);
			} catch (MalformedJwtException e) {
				request.setAttribute(reqAttributeName, "Malformed JWT");
				throw new BadCredentialsException("Malformed JWT", e);
			} catch (Exception e) {
				request.setAttribute(reqAttributeName, "JWT validation failed: " + e.getMessage());
				throw new BadCredentialsException("JWT validation failed: " + e.getMessage(), e);
			}
		}
		filterChain.doFilter(request, response);
	}
}
