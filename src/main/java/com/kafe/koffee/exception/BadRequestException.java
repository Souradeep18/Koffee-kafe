package com.kafe.koffee.exception;

@SuppressWarnings("serial")
public class BadRequestException extends RuntimeException {
	
	public BadRequestException(String message) {
		super(message);
	}

}
