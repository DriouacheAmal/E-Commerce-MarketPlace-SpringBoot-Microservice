package com.example.Security.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordDto {
    private String newPassword;
    private String matchPassword;
    private String token;
}
