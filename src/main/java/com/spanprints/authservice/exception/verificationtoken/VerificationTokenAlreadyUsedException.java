package com.spanprints.authservice.exception.verificationtoken;

public class VerificationTokenAlreadyUsedException extends RuntimeException {

	private static final long serialVersionUID = 501784983155667964L;

	public VerificationTokenAlreadyUsedException() {
		super();
	}

	public VerificationTokenAlreadyUsedException(String message) {
		super(message);
	}

}