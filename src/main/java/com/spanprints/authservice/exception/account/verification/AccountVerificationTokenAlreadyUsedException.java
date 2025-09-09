package com.spanprints.authservice.exception.account.verification;

public class AccountVerificationTokenAlreadyUsedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8830854733339183818L;

	public AccountVerificationTokenAlreadyUsedException() {
		super();
	}

	public AccountVerificationTokenAlreadyUsedException(String message) {
		super(message);
	}

}