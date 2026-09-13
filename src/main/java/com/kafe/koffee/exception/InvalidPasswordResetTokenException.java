package com.kafe.koffee.exception;

@SuppressWarnings("serial")
public class InvalidPasswordResetTokenException extends RuntimeException {

    public InvalidPasswordResetTokenException(String message) {
        super(message); }

}
