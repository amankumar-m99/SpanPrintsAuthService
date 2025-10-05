package com.spanprints.authservice.exception.profilepic;

public class ProfilePicNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5864416960196474312L;

	public ProfilePicNotFoundException() {
		super();
	}

	public ProfilePicNotFoundException(String message) {
		super(message);
	}

}
