package com.spanprints.authservice.exception.account.verification;

public class AccountVerificationTokenNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8830854733339183818L;

	public AccountVerificationTokenNotFoundException() {
		super();
	}

	public AccountVerificationTokenNotFoundException(String message) {
		super(message);
	}

}