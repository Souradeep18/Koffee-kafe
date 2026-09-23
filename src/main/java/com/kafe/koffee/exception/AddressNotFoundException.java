package com.kafe.koffee.exception;

@SuppressWarnings("serial")
public class AddressNotFoundException extends ResourceNotFoundException{

	public AddressNotFoundException(String message) {
		super(message);
	}

}
