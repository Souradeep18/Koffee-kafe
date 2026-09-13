package com.kafe.koffee.exception;

@SuppressWarnings("serial")
public class CouponNotFoundException extends ResourceNotFoundException {

    public CouponNotFoundException(String message) {
        super(message); }

}
