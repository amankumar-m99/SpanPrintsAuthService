package com.spanprints.authservice.exception.profilepic;

public class ProfilePicFormatNotAcceptedException extends RuntimeException {

	private static final long serialVersionUID = -9162142087526229849L;

	public ProfilePicFormatNotAcceptedException() {
		super();
	}

	public ProfilePicFormatNotAcceptedException(String message) {
		super(message);
	}

}
