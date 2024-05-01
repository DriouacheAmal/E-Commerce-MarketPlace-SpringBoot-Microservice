package com.example.Security.Service;

import com.example.Security.Dto.ChangePasswordDto;
import com.example.Security.Dto.RegisterDto;
import com.example.Security.Dto.TokenDto;
import com.example.Security.Exceptions.DataNotValidException;
import com.example.Security.Request.LoginRequest;
import com.example.User.Entity.DTO.ChangePasswordDTO;
import org.springframework.http.ResponseEntity;

public interface AuthServiceImpl {

    void validateRegistration(RegisterDto request) throws DataNotValidException;
    TokenDto login(LoginRequest request);
    RegisterDto register(RegisterDto request);
    ResponseEntity<?> confirmEmail(String confirmationToken);

    ResponseEntity<String> handleChangePassword(ChangePasswordDto changePasswordDto) throws DataNotValidException;


}
