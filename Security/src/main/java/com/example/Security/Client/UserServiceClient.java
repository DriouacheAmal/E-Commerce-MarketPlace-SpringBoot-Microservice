package com.example.Security.Client;

import com.example.Security.Dto.ChangePasswordDto;
import com.example.Security.Dto.RegisterDto;
import com.example.Security.Dto.UserDto;
import com.example.User.Entity.DTO.ChangePasswordDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "User")
public interface UserServiceClient {
   // @PostMapping("/users/register")
    //ResponseEntity<RegisterDto> save (@RequestBody RegisterDto request);

    @PostMapping("/users/register")
    RegisterDto register(@RequestBody RegisterDto request);

    @GetMapping("/users/getUserByEmail/{email}")
    ResponseEntity<UserDto> getUserByEmail(@PathVariable String email);

    @GetMapping("/users/username={username}")
    ResponseEntity<UserDto> getUserByUsername(@PathVariable String username);

    @GetMapping("/users/existsByEmail/{email}")
    boolean existsByEmail(@PathVariable String email);

    @GetMapping("/users/account-confirmation")
    ResponseEntity<?> confirmUserAccount(@RequestParam("token") String confirmationToken);

    @GetMapping("/users/handleChangePassword")
    ResponseEntity<String> handleChangePassword(@RequestBody ChangePasswordDto changePasswordDto);


}
