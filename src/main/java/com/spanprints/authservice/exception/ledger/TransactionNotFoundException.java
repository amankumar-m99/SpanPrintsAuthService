package com.spanprints.authservice.exception.ledger;

public class TransactionNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4573867020204578454L;

	public TransactionNotFoundException() {
		super();
	}

	public TransactionNotFoundException(String message) {
		super(message);
	}

}
