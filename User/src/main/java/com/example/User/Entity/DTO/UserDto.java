package com.example.User.Entity.DTO;

import com.example.User.enums.Active;
import com.example.User.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jdk.jfr.Category;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Long id;
    private Long userId;
    private String firstname;
    private String lastname;
    private String username;
    private String phoneNumber;
    private String email;
    private String password;
    private UserRole userRole;
    private Active active;
    private Active isActive;
    private boolean isEnabled;
    private String confirmationToken;
    //private List<Category> categories;
    private String resetPasswordToken;
    @ElementCollection(targetClass = UserRole.class)
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles;


    /*public String getResetPasswordToken() {
        return this.resetPasswordToken;


    }

     */


}
