package com.spanprints.authservice.exception.verificationtoken;

public class AccountVerificationTokenExpiredException extends RuntimeException {

	private static final long serialVersionUID = -7736170734625336164L;

	public AccountVerificationTokenExpiredException() {
		super();
	}

	public AccountVerificationTokenExpiredException(String message) {
		super(message);
	}

}