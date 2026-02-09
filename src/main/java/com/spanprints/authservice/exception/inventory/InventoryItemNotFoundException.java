package com.spanprints.authservice.exception.inventory;

public class InventoryItemNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 6197728126574479470L;

	public InventoryItemNotFoundException() {
		super();
	}

	public InventoryItemNotFoundException(String message) {
		super(message);
	}

	public InventoryItemNotFoundException(String property, Object obj) {
		super("No Item found with " + property + " " + obj);
	}

}
