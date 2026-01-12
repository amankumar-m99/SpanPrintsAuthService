package com.spanprints.authservice.exception.printjob;

public class PrintJobTypeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -1929640199774259269L;

	public PrintJobTypeNotFoundException() {
		super();
	}

	public PrintJobTypeNotFoundException(String message) {
		super(message);
	}

}