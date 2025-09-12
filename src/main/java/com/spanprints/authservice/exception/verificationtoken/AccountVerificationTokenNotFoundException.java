package com.spanprints.authservice.exception.verificationtoken;

public class AccountVerificationTokenNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5547023544347417354L;

	public AccountVerificationTokenNotFoundException() {
		super();
	}

	public AccountVerificationTokenNotFoundException(String message) {
		super(message);
	}

}