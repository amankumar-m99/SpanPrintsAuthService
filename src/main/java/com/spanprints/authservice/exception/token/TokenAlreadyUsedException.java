package com.spanprints.authservice.exception.token;

public class TokenAlreadyUsedException extends RuntimeException {

	private static final long serialVersionUID = 501784983155667964L;

	public TokenAlreadyUsedException() {
		super();
	}

	public TokenAlreadyUsedException(String message) {
		super(message);
	}

}