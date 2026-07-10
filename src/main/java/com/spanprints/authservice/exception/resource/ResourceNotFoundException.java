package com.spanprints.authservice.exception.resource;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5547023544347417354L;

	public ResourceNotFoundException() {
		super();
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}

}