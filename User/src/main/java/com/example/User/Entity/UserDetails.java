package com.example.User.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetails {
    private String username;
    private String phoneNumber;
    private String country;
    private String city;
    private String address;
    private String postalCode;
    private String profilePicture;
}
