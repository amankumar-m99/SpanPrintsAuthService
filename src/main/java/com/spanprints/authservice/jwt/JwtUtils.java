package com.spanprints.authservice.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

	public static final long JWT_TOKEN_VALIDITY = 2 * 60 * 60L; // 2 hours validity

	private static final String SECRET_KEY = "TaK+HaV^uvCHEFsEVfypW#7g9^k*Z8$V";

	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}

	public JwtResponseDto generateJwtResponseDto(UserDetails userDetails) {
		Date expiryDate = new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000);
		String token = generateToken(userDetails, expiryDate);
		return new JwtResponseDto(token, "Bearer", expiryDate.toString());
	}

	public String generateToken(UserDetails userDetails) {
		return generateToken(userDetails, new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000));
	}

	public String generateToken(UserDetails userDetails, Date expiryDate) {
		Map<String, Object> claims = new HashMap<>();
		List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
		claims.put("roles", roles);
		return Jwts.builder().claims(claims).subject(userDetails.getUsername()).header().empty().add("typ", "JWT").and()
				.issuedAt(new Date(System.currentTimeMillis())).expiration(expiryDate).signWith(getSigningKey())
				.compact();
	}

	public String extractUsername(String token) {
		Claims claims = extractAllClaims(token);
		return claims.getSubject();
	}

	public Date extractExpiration(String token) {
		return extractAllClaims(token).getExpiration();
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public boolean validateToken(String token) {
		return !isTokenExpired(token);
	}
}
