package com.kafe.koffee.exception;

@SuppressWarnings("serial")
public class PaymentNotFoundException extends ResourceNotFoundException {

    public PaymentNotFoundException(String message) {
        super(message); }

}
