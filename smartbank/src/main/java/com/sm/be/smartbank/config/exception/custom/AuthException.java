package com.sm.be.smartbank.config.exception.custom;

import org.springframework.core.NestedRuntimeException;

public class AuthException extends NestedRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3993393631806952887L;

	public AuthException(String message) {
		super(message);
	}

	public AuthException(String message, Throwable cause) {
		super(message, cause);
	}
}
