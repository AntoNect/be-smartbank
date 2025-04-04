package com.sm.be.smartbank.config.exception.custom;

import org.springframework.core.NestedRuntimeException;

public class ChkException extends NestedRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3993393631806952887L;

	public ChkException(String message) {
		super(message);
	}

	public ChkException(String message, Throwable cause) {
		super(message, cause);
	}
}
