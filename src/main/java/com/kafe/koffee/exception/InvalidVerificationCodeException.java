package com.kafe.koffee.exception;

@SuppressWarnings("serial")
public class InvalidVerificationCodeException extends RuntimeException {

    public InvalidVerificationCodeException(String message) {
        super(message);}

}
