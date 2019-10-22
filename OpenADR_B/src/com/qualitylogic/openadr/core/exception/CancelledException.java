package com.qualitylogic.openadr.core.exception;

public class CancelledException extends RuntimeException {

	public CancelledException() {
		super("TestCase execution was cancelled by the user");
	}

	public CancelledException(String message) {
		super(message);
	}

	private static final long serialVersionUID = -6764579619461818867L;
}
