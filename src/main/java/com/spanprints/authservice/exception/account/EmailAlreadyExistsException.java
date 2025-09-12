package com.spanprints.authservice.exception.account;

public class EmailAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -419504435989737502L;

	public EmailAlreadyExistsException() {
		super();
	}

	public EmailAlreadyExistsException(String message) {
		super(message);
	}

}