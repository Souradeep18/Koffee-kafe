package com.kafe.koffee.exception;

@SuppressWarnings("serial")
public class MenuItemNotFoundException extends ResourceNotFoundException {
    public MenuItemNotFoundException(String message) {
        super(message);

}
}
