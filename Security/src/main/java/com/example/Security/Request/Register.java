package com.example.Security.Request;

import com.example.Security.Dto.RegisterDto;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Register {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
}
