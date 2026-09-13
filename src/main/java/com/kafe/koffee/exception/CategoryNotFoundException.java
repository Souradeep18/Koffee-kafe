package com.kafe.koffee.exception;

@SuppressWarnings("serial")
public class CategoryNotFoundException extends ResourceNotFoundException {
    public CategoryNotFoundException(String message) {
        super(message);}

}
