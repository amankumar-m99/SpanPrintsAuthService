package com.spanprints.authservice.exception.inventory;

public class ItemAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 9059700317921976350L;

	public ItemAlreadyExistsException() {
		super();
	}

	public ItemAlreadyExistsException(String message) {
		super(message);
	}

}
