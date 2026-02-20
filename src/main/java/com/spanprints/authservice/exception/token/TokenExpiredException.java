package com.spanprints.authservice.exception.token;

public class TokenExpiredException extends RuntimeException {

	private static final long serialVersionUID = -7736170734625336164L;

	public TokenExpiredException() {
		super();
	}

	public TokenExpiredException(String message) {
		super(message);
	}

}