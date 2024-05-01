package com.example.User.Controller;

import com.example.User.Entity.DTO.UserDto;
import com.example.User.Entity.User;
import com.example.User.Exceptions.EmailAlreadyExistsException;
import com.example.User.Exceptions.EmptyEntityException;
import com.example.User.Exceptions.UserNotFoundException;
import com.example.User.Service.UserService;
import com.example.User.Utils.UserValidation;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User userDto) {
        try {
            userService.registerUser(userDto);
            return ResponseEntity.ok("Verify email by the link sent to your email address");
        } catch (MessagingException | RoleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send confirmation email: " + e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUsers() {
        try {
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch users", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {

        try {
            UserDto user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (EmptyEntityException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /*@PostMapping("/reset-password")
    public ResponseEntity<String> ResetPassword(@RequestParam("email") String email) throws MessagingException {
        userService.resetPassword(email);
        return ResponseEntity.ok("a reset password email was send to: " + email);
    }

     */

    @GetMapping("/active")
    public ResponseEntity<List<UserDto>> getActiveUsers() {
        try {
            List<UserDto> activeUsers = userService.getActiveUsers();
            return ResponseEntity.ok(activeUsers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<UserDto>> getInactiveUsers() {
        try {
            List<UserDto> inactiveUsers = userService.getInactiveUsers();
            return ResponseEntity.ok(inactiveUsers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        try {
            UserDto updatedUser = userService.updateUser(id, userDto);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        try {
            UserDto user = userService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.ok(userService.getUserByEmail(email));
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUserById(@PathVariable Long id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException | EmptyEntityException e) {

            return ResponseEntity.ok(userService.getUserById(id));
        }


    }

    @GetMapping("/username={username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        try {
            UserDto user = userService.getUserByUsername(username);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {

            return ResponseEntity.notFound().build();
        }


    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam("email") String email) {
        try {
            userService.resetPassword(email);
            return ResponseEntity.ok("Reset password instructions sent to your email address");
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send reset password instructions");
        }
    }
    @GetMapping("/existsByEmail/{email}")
    public boolean existsByEmail(@PathVariable String email) {
        return userService.existsByEmail(email);
    }

}


