package com.qualitylogic.openadr.core.exception;

public class ValidationException extends RuntimeException {

	public ValidationException() {
		super("Validation failed.");
	}

	public ValidationException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 4173347823746540062L;
}
