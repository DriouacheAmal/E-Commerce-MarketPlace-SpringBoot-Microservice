package com.example.Order.exception;

public class RentalItemNotFoundException extends RuntimeException{
    public RentalItemNotFoundException(String message) {
        super(message);
    }
}
