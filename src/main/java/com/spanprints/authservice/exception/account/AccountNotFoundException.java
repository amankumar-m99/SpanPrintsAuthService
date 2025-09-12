package com.spanprints.authservice.exception.account;

public class AccountNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -7126826979767981587L;

	public AccountNotFoundException() {
		super();
	}

	public AccountNotFoundException(String message) {
		super(message);
	}

}