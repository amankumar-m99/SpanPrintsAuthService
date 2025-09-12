package com.spanprints.authservice.exception.account;

public class UsernameAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -8699523327211339754L;

	public UsernameAlreadyExistsException() {
		super();
	}

	public UsernameAlreadyExistsException(String message) {
		super(message);
	}

}