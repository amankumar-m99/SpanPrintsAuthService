package com.spanprints.authservice.exception.verificationtoken;

public class VerificationTokenNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5547023544347417354L;

	public VerificationTokenNotFoundException() {
		super();
	}

	public VerificationTokenNotFoundException(String message) {
		super(message);
	}

}