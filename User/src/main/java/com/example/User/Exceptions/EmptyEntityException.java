package com.example.User.Exceptions;

public class EmptyEntityException extends RuntimeException {
    public EmptyEntityException(String message) {
        super(message);
    }
}
