package com.spanprints.authservice.exception.customer;

public class CustomerAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 8710850575060810402L;

	public CustomerAlreadyExistsException() {
		super();
	}

	public CustomerAlreadyExistsException(String message) {
		super(message);
	}

}
