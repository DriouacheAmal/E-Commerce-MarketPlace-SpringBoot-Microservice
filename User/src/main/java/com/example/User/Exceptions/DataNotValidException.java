package com.example.User.Exceptions;

public class DataNotValidException extends RuntimeException{
    public DataNotValidException(String message) {
        super(message);
    }
}
