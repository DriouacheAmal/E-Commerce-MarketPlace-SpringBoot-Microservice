package com.example.User.Service;

import com.example.User.Entity.DTO.ChangePasswordDTO;
import com.example.User.Entity.DTO.UserDto;
import com.example.User.Entity.User;
import com.example.User.Exceptions.EmailAlreadyExistsException;
import com.example.User.Exceptions.EmptyEntityException;
import com.example.User.Exceptions.UserNotFoundException;
//import jakarta.mail.MessagingException;
import com.example.User.enums.UserRole;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers() throws UserNotFoundException;

    List<UserDto> getActiveUsers();
    List<UserDto> getInactiveUsers();

    UserDto getUserById(Long userId) throws UserNotFoundException, EmptyEntityException;
    UserDto getUserByEmail(String email) throws UserNotFoundException;
    //void resetPassword(String email) throws MessagingException;

    UserDto updateUser(Long userId, UserDto userDto) throws UserNotFoundException;

    UserDto getUserByUsername(String username) throws UserNotFoundException;

    void deleteById(Long userId) throws  UserNotFoundException, EmptyEntityException;

    ResponseEntity<?> confirmEmail(String confirmationToken);

    void resetPassword(String email) throws MessagingException;
    void registerUser(User userDto) throws MessagingException, RoleNotFoundException;

    boolean existsByEmail(String email);

    void changePassword(ChangePasswordDTO dto);

    //void registerUser(UserDto userDto) throws MessagingException, RoleNotFoundException;
}
