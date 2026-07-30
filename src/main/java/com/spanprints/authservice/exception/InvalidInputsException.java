package com.spanprints.authservice.exception;

public class InvalidInputsException extends RuntimeException {

	private static final long serialVersionUID = -4671162520549402517L;

	public InvalidInputsException() {
		super();
	}

	public InvalidInputsException(String message) {
		super(message);
	}
}
