package com.spanprints.authservice.exception.account;

public class UsernameAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8830854733339183818L;

	public UsernameAlreadyExistsException() {
		super();
	}

	public UsernameAlreadyExistsException(String message) {
		super(message);
	}

}