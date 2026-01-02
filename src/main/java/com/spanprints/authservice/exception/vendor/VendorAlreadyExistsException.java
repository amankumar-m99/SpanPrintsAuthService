package com.spanprints.authservice.exception.vendor;

public class VendorAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 3986295700963616796L;

	public VendorAlreadyExistsException() {
		super();
	}

	public VendorAlreadyExistsException(String message) {
		super(message);
	}

}
