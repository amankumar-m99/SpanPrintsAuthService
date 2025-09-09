package com.spanprints.authservice.exception.role;

public class RoleNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2849879468985919804L;

	public RoleNotFoundException() {
		super();
	}

	public RoleNotFoundException(String message) {
		super(message);
	}

}
