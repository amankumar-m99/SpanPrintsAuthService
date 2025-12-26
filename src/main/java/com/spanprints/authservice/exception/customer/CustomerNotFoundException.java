package com.spanprints.authservice.exception.customer;

public class CustomerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 6197728126574479470L;

	public CustomerNotFoundException() {
		super();
	}

	public CustomerNotFoundException(String message) {
		super(message);
	}

	public CustomerNotFoundException(String property, Object obj) {
		super("No customer found with " + property + " " + obj);
	}

}
