package com.example.User;


import com.example.User.Entity.User;
import com.example.User.Repository.UserRepository;
import com.example.User.Service.Impl.UserServiceImpl;
import com.example.User.enums.Active;
import com.example.User.enums.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.Set;

@SpringBootApplication
@AllArgsConstructor
public class UserApplication {
    private final UserRepository userRepository;
    private final UserServiceImpl userServiceImpl;

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            UserRole userRole = UserRole.CUSTOMER;
            UserRole adminRole = UserRole.ADMIN;

            User toUser = User.builder()
                    .username("username1")
                    .firstname("Amal")
                    .lastname("drch")
                    .email("amal98drch@gmail.com")
                    .phoneNumber("0648486585")
                    .password("Ahihi9856")
                    .roles(Set.of(userRole, adminRole))
                    .active(Active.ACTIVE)
                    .build();

            User u = User.builder()
                    .username("username2")
                    .firstname("Oumaima")
                    .lastname("Marah")
                    .email("oumaima_Marah@gmail.com")
                    .phoneNumber("0685974128")
                    .password("Phjdhhdk9856")
                    .roles(Set.of(userRole))
                    .active(Active.ACTIVE)
                    .build();




            userServiceImpl.registerUser(toUser);
           // userServiceImpl.registerUser(u);
        };

    }
}


