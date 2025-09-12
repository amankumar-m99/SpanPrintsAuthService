package com.spanprints.authservice.exception.verificationtoken;

public class AccountVerificationTokenAlreadyUsedException extends RuntimeException {

	private static final long serialVersionUID = 501784983155667964L;

	public AccountVerificationTokenAlreadyUsedException() {
		super();
	}

	public AccountVerificationTokenAlreadyUsedException(String message) {
		super(message);
	}

}