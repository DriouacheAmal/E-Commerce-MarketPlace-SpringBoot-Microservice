package com.example.Security.Dto;

import com.example.Security.Enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private String id;
    private String username;
    private String email;
    private String lastname;
    private String password;
    private UserRole userRole;
}
