package com.example.User.enums;

public enum CustomerEmailMessage {
    RESET_PASSWORD_SUBJECT("Password Reset Request");

    private final String message;

    CustomerEmailMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}


