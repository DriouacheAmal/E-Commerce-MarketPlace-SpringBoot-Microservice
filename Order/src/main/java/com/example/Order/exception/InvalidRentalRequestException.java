package com.example.Order.exception;

public class InvalidRentalRequestException extends RuntimeException {
    public InvalidRentalRequestException() {
        super();
    }

    public InvalidRentalRequestException(String message) {
        super(message);
    }

    public InvalidRentalRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRentalRequestException(Throwable cause) {
        super(cause);
    }
}

