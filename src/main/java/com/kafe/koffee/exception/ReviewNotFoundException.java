package com.kafe.koffee.exception;

@SuppressWarnings("serial")
public class ReviewNotFoundException extends ResourceNotFoundException {

    public ReviewNotFoundException(String message) {
        super(message);
    } 

}
