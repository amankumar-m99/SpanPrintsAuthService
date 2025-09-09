package com.spanprints.authservice.exception.account;

public class EmailAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8830854733339183818L;

	public EmailAlreadyExistsException() {
		super();
	}

	public EmailAlreadyExistsException(String message) {
		super(message);
	}

}