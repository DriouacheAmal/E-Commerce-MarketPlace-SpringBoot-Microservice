package com.example.Order.exception;

public class RentalItemAlreadyReservedException extends RuntimeException{
    public RentalItemAlreadyReservedException(String message) {
        super(message);
    }
}
