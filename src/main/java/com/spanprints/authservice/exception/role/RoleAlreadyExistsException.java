package com.spanprints.authservice.exception.role;

public class RoleAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8830854733339183818L;

	public RoleAlreadyExistsException() {
		super();
	}

	public RoleAlreadyExistsException(String message) {
		super(message);
	}

}