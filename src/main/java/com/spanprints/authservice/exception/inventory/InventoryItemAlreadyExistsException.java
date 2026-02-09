package com.spanprints.authservice.exception.inventory;

public class InventoryItemAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 9059700317921976350L;

	public InventoryItemAlreadyExistsException() {
		super();
	}

	public InventoryItemAlreadyExistsException(String message) {
		super(message);
	}

}
