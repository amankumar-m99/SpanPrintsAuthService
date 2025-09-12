package com.spanprints.authservice.exception.role;

public class RoleNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5293361912281537468L;

	public RoleNotFoundException() {
		super();
	}

	public RoleNotFoundException(String message) {
		super(message);
	}

}
