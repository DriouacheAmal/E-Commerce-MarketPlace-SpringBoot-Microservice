package com.example.User.Utils;

import com.example.User.Entity.DTO.ErrorBody;
import com.example.User.Entity.DTO.UserDto;
import com.example.User.enums.MessagesError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
@Component
@RequiredArgsConstructor
public class UserValidation {
    private static Boolean isNull(String field) {
        return field == null || field.trim().isEmpty();
    }

    private static Boolean isValidEmail(String email) {
        final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        var matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
    private static Boolean isValidPhoneNumber(String phoneNumber) {
        // Customize phone number pattern according to your requirements
        final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\d{10}$");
        return PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches();
    }
    private static Boolean isValidUsername(String username) {
        final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]*$");
        return USERNAME_PATTERN.matcher(username).matches();
    }
    private static Boolean isValidPassword(String password) {
        // Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character
        final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
        return PASSWORD_PATTERN.matcher(password).matches();
    }
/*
    public static List<Object> validate(UserDto userDto) {
        ArrayList<ErrorBody> errors = new ArrayList<>();

        if (isNull(userDto.getFirstname())) {
            errors.add(new ErrorBody(MessagesError.FIRSTNAME_IS_REQUIRED.getMessage()));
        }

        if (isNull(userDto.getPassword())) {
            errors.add(new ErrorBody(MessagesError.PASSWORD_IS_REQUIRED.getMessage()));
        }

        if (isNull(userDto.getLastname())) {
            errors.add(new ErrorBody(MessagesError.LASTNAME_IS_REQUIRED.getMessage()));
        }

        if (isNull(userDto.getUsername())) {
            errors.add(new ErrorBody(MessagesError.USERNAME_IS_REQUIRED.getMessage()));
        }

        if (isNull(userDto.getEmail())) {
            errors.add(new ErrorBody(MessagesError.EMAIL_IS_REQUIRED.getMessage()));

        } else if (!isValidEmail(userDto.getEmail())) {
            errors.add(new ErrorBody(MessagesError.EMAIL_IS_INVALID.getMessage()));
        }

        return errors;
    }
*/
}
