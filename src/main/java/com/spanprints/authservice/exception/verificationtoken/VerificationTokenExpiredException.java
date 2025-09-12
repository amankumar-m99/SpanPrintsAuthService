package com.spanprints.authservice.exception.verificationtoken;

public class VerificationTokenExpiredException extends RuntimeException {

	private static final long serialVersionUID = -7736170734625336164L;

	public VerificationTokenExpiredException() {
		super();
	}

	public VerificationTokenExpiredException(String message) {
		super(message);
	}

}