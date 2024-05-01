package com.example.Security.Enums;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum CustomerMessageError {
    SAVED_SUCCESSFULLY("Saved successfully"),
    CHECK_EMAIL_FOR_VALIDATION_YOUR_MAIL("Please check your email for validation"),
    PASSWORD_LENGTH_ERROR("Password length error"),
    PHONE_NUMBER_NOT_VALID("Phone number is not valid"),
    EMAIL_IS_INVALID("Email is invalid"),
    FIRSTNAME_IS_REQUIRED("Firstname is required"),
    LASTNAME_IS_REQUIRED("Lastname is required"),
    EMAIL_ALREADY_EXIST("Email already exists"),
    INVALID_REQUEST("Invalid Request"),
    PASSWORD_MATCH_ERROR("No matching password found.");

    private final String message;

    CustomerMessageError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
