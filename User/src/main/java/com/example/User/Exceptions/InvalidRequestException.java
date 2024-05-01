package com.example.User.Exceptions;

import org.modelmapper.spi.ErrorMessage;

import java.util.List;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(List<ErrorMessage> validationErrors) {
    }
}
