package com.kafe.koffee.exception;

@SuppressWarnings("serial")
public class CartItemNotFoundException extends ResourceNotFoundException {

    public CartItemNotFoundException(String message) {
        super(message);
    }

}
