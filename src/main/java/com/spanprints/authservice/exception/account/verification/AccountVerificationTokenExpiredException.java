package com.spanprints.authservice.exception.account.verification;

public class AccountVerificationTokenExpiredException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8830854733339183818L;

	public AccountVerificationTokenExpiredException() {
		super();
	}

	public AccountVerificationTokenExpiredException(String message) {
		super(message);
	}

}