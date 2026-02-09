package com.spanprints.authservice.exception.inventory;

public class ItemNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 6197728126574479470L;

	public ItemNotFoundException() {
		super();
	}

	public ItemNotFoundException(String message) {
		super(message);
	}

	public ItemNotFoundException(String property, Object obj) {
		super("No Item found with " + property + " " + obj);
	}

}
