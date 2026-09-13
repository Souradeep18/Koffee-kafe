package com.kafe.koffee.exception;

@SuppressWarnings("serial")
public class OrderNotFoundException extends ResourceNotFoundException {

    public OrderNotFoundException(String message) {
        super(message);
    } 

}
