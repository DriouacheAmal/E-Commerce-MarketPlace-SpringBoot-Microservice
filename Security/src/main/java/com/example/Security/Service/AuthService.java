package com.example.Security.Service;

import com.example.Security.Client.UserServiceClient;
import com.example.Security.Dto.ChangePasswordDto;
import com.example.Security.Dto.RegisterDto;
import com.example.Security.Dto.TokenDto;
import com.example.Security.Enums.CustomerMessageError;
import com.example.Security.Enums.CustomerMessageValidator;
import com.example.Security.Exceptions.DataNotValidException;
import com.example.Security.Exceptions.WrongCredentialsException;
import com.example.Security.Request.LoginRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Service
@AllArgsConstructor
public class AuthService implements AuthServiceImpl{
    private final AuthenticationManager authenticationManager;
    private final UserServiceClient userServiceClient;
    private final JWTService jwtService;


    public TokenDto login(LoginRequest request) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            if (authenticate.isAuthenticated()) {
                String token = jwtService.generateToken(request.getEmail());
                return TokenDto.builder().token(token).build();
            } else {
                throw new WrongCredentialsException("Wrong credentials");
            }
        } catch (AuthenticationException e) {
            throw new WrongCredentialsException("Wrong credentials");
        }
    }

    public RegisterDto register(RegisterDto request) {
        try {
            validateRegistration(request);
            //System.out.println("5688");
            userServiceClient.register(request);
            return RegisterDto.builder()
                    .message(CustomerMessageValidator.SAVED_SUCCESSFULLY.getMessage() + " please "
                            + CustomerMessageValidator.CHECK_EMAIL_FOR_VALIDATING_YOUR_EMAIL.getMessage())
                    .build();
        } catch (DataNotValidException e) {
            // Instead of rethrowing the exception, you can return a RegisterDto with the error message
            return RegisterDto.builder()
                    .message("Registration failed: " + e.getMessage())
                    .build();
        }

    }
    public void validateRegistration(RegisterDto request) throws DataNotValidException {
        if (userServiceClient.existsByEmail(request.getEmail())) {
            throw new DataNotValidException(CustomerMessageError.EMAIL_ALREADY_EXIST.getMessage());
        }
        if (isNull(request.getUsername())) {
            throw new DataNotValidException("Username is required."); // Corrected error message
        }
        if (!isValidEmail(request.getEmail())) {
            throw new DataNotValidException(CustomerMessageError.EMAIL_IS_INVALID.getMessage());
        }
        if (!isValidPassword(request.getPassword())) {
            throw new DataNotValidException(CustomerMessageError.PASSWORD_LENGTH_ERROR.getMessage());
        }
    }

    // Validation methods moved from AuthenticationManager to AuthService
    private boolean isValidPassword(String password) {
        // Password must be at least 8 characters long
        return password != null && password.length() >= 8;
    }



    private boolean isValidEmail(String email) {
        // For simplicity, use a basic email pattern matching
        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    private boolean isNull(String value) {
        return value == null || value.trim().isEmpty();
    }



    @Override
    public ResponseEntity<?> confirmEmail(String confirmationToken) {
        return userServiceClient.confirmUserAccount(confirmationToken);
    }

    @Override
    public ResponseEntity<String> handleChangePassword(ChangePasswordDto changePasswordDto) throws DataNotValidException {
        if (changePasswordDto.getToken() == null || changePasswordDto.getToken().isEmpty())
            throw new DataNotValidException(CustomerMessageError.INVALID_REQUEST.getMessage());
        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getMatchPassword()))
            throw new RuntimeException(CustomerMessageError.PASSWORD_MATCH_ERROR.getMessage());
        return userServiceClient.handleChangePassword(changePasswordDto);
    }


}
