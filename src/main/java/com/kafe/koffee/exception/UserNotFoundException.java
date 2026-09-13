package com.kafe.koffee.exception;

@SuppressWarnings("serial")
public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }

}
