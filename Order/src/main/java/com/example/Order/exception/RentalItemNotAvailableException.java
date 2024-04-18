package com.example.Order.exception;

public class RentalItemNotAvailableException extends RuntimeException{
    public RentalItemNotAvailableException(String message) {
        super(message);
    }
}
