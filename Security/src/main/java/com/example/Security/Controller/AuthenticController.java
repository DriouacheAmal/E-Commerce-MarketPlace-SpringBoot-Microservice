package com.example.Security.Controller;

import com.example.Security.Client.UserServiceClient;
import com.example.Security.Dto.ChangePasswordDto;
import com.example.Security.Dto.RegisterDto;
import com.example.Security.Dto.TokenDto;
import com.example.Security.Exceptions.DataNotValidException;
import com.example.Security.Request.LoginRequest;
import com.example.Security.Service.AuthService;
import com.example.Security.Service.CustomerUserDetailsImp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthenticController {
    @Autowired
    private final AuthService authService;
    private UserServiceClient userServiceClient;

    private final CustomerUserDetailsImp customerUserDetailsImp;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterDto> register(@RequestBody RegisterDto request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @GetMapping("/confirm-account")
    public ResponseEntity<?> validationEmail(@RequestParam("token") String confirmationToken) {
        return authService.confirmEmail(confirmationToken);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> handleChangePassword(@RequestParam("token") String token, @RequestBody ChangePasswordDto changePasswordDto) throws DataNotValidException {
        changePasswordDto.setToken(token);
        authService.handleChangePassword(changePasswordDto);
        return ResponseEntity.ok("Password changed successfully");

    }


}



