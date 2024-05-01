package com.example.User.Entity.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordDTO {
    private String newPassword;
    private String matchPassword;
    private String token;

}
