package com.spanprints.authservice.exception.printjob;

public class PrintJobNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 6007597983196707112L;

	public PrintJobNotFoundException() {
		super();
	}

	public PrintJobNotFoundException(String message) {
		super(message);
	}

}