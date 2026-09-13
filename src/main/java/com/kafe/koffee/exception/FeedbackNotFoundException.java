package com.kafe.koffee.exception;

@SuppressWarnings("serial")
public class FeedbackNotFoundException extends ResourceNotFoundException {

    public FeedbackNotFoundException(String message) {
        super(message);
    } 

}
