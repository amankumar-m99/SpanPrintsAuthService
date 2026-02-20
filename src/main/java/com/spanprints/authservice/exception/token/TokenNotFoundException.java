package com.spanprints.authservice.exception.token;

public class TokenNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5547023544347417354L;

	public TokenNotFoundException() {
		super();
	}

	public TokenNotFoundException(String message) {
		super(message);
	}

}