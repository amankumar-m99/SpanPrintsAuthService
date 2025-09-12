package com.spanprints.authservice.exception.role;

public class RoleAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -1367988388863087756L;

	public RoleAlreadyExistsException() {
		super();
	}

	public RoleAlreadyExistsException(String message) {
		super(message);
	}

}