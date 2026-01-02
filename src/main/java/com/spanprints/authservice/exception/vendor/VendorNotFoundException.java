package com.spanprints.authservice.exception.vendor;

public class VendorNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6204398392059118838L;

	public VendorNotFoundException() {
		super();
	}

	public VendorNotFoundException(String message) {
		super(message);
	}

	public VendorNotFoundException(String property, Object obj) {
		super("No vendor found with " + property + " " + obj);
	}

}
