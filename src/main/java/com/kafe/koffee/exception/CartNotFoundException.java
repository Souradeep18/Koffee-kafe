package com.kafe.koffee.exception;

@SuppressWarnings("serial")
public class CartNotFoundException extends ResourceNotFoundException {
	
    public CartNotFoundException(String message) {
        super(message);}

}
