package com.example.Security.Enums;

import lombok.Getter;
import lombok.Setter;

@Getter

public enum CustomerMessageValidator {
    SAVED_SUCCESSFULLY("Saved successfully"),
    CHECK_EMAIL_FOR_VALIDATING_YOUR_EMAIL("Please check your email for validation");

    private final String message;

    CustomerMessageValidator(String message) {

        this.message = message;
    }

    public String getMessage() {

        return message;
    }
}
