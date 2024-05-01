package com.example.Security.Service;

import com.example.Security.Client.UserServiceClient;
import com.example.Security.Dto.RegisterDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service

public class CustomerUserDetailsImp implements UserDetailsService {
    private final UserServiceClient userServiceClient;


    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        var user = userServiceClient.getUserByEmail(email).getBody();
        assert user != null;
        return new CustomerUserDetails(user);
    }
    public CustomerUserDetailsImp(UserServiceClient userServiceClient) {

        this.userServiceClient = userServiceClient;
    }


}
