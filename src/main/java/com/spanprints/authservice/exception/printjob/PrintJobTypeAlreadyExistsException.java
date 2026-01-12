package com.spanprints.authservice.exception.printjob;

public class PrintJobTypeAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 7377658381018370739L;

	public PrintJobTypeAlreadyExistsException() {
		super();
	}

	public PrintJobTypeAlreadyExistsException(String message) {
		super(message);
	}

}