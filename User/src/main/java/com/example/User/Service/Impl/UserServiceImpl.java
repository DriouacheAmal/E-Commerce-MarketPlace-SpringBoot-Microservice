package com.example.User.Service.Impl;

import com.example.User.Email.EmailServiceImpl;
import com.example.User.Entity.DTO.ChangePasswordDTO;
import com.example.User.Entity.DTO.UserDto;
import com.example.User.Entity.User;
import com.example.User.Exceptions.DataNotValidException;
import com.example.User.Exceptions.EmptyEntityException;
import com.example.User.Exceptions.UserNotFoundException;
import com.example.User.Mappers.MappingProfile;
import com.example.User.Repository.UserRepository;
import com.example.User.Service.UserService;
import com.example.User.Utils.TokenGenerator;
import com.example.User.enums.Active;
import com.example.User.enums.CustomerEmailMessage;
import com.example.User.enums.MessagesError;
import com.example.User.enums.UserRole;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.management.relation.RoleNotFoundException;
import java.util.*;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final EmailServiceImpl emailServiceImpl;


    @Override
    public List<UserDto> getAllUsers() {
        log.info("Fetching all users");
        List<User> users = userRepository.findAll(); // Get all users from database
        List<UserDto> userDtos = users.stream()
                .map(MappingProfile::mapToUserDto)
                .collect(Collectors.toList()); // Mapper all users to UserDto nd collect in a list
        return userDtos; // return list UserDto
    }

    @Override
    public List<UserDto> getActiveUsers() {
        log.info("Fetching active users");
        List<User> activeUsers = userRepository.findByActive(Active.ACTIVE);
        return activeUsers.stream()
                .map(MappingProfile::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getInactiveUsers() {
        log.info("Fetching inactive users");
        List<User> inactiveUsers = userRepository.findByActive(Active.INACTIVE);
        return inactiveUsers.stream()
                .map(MappingProfile::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long userId) throws UserNotFoundException, EmptyEntityException {
        log.info("Fetching user by id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        MessagesError.USER_NOT_FOUND_WITH_ID_EQUALS.getMessage() + userId));

        return MappingProfile.mapToUserDto(user);

    }

    @Override
    public UserDto getUserByEmail(String email) throws UserNotFoundException {
        log.info("Fetching user by email: {}", email);
        return userRepository.findByEmail(email)
                .map(MappingProfile::mapToUserDto)
                .orElseThrow(() -> new UserNotFoundException(
                        MessagesError.USER_NOT_FOUND_WITH_EMAIL_EQUALS.getMessage() + email));
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) throws UserNotFoundException {
        log.info("Creating new user: {}", userDto.getEmail());
        var user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(MessagesError.USER_NOT_FOUND.getMessage()));
        return MappingProfile.mapToUserDto(userRepository.save(user));

    }

    @Override
    public void deleteById(Long userId) throws UserNotFoundException, EmptyEntityException {
        log.info("delete user by id : {}", userId);
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(MessagesError.USER_NOT_FOUND_WITH_ID_EQUALS.getMessage() + userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public UserDto getUserByUsername(String username) throws UserNotFoundException {
        log.info("Fetching user by username: {}", username);
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException(
                    MessagesError.USER_NOT_FOUND_WITH_USERNAME_EQUALS.getMessage() + username);
        } else {
            return MappingProfile.mapToUserDto(user.get());
        }
    }

    @Override
    public ResponseEntity<?> confirmEmail(String confirmationToken) {
        User user = userRepository.findByConfirmationToken(confirmationToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid token!"));
        user.setVerifiedAt(new Date());
        user.setEnabled(true);
        userRepository.save(user);
        return ResponseEntity.ok("Your Email confirmed successfully.");
    }

    @Override
    public void resetPassword(String email) throws MessagingException {
        if (email == null) {
            throw new DataNotValidException(MessagesError.EMAIL_IS_REQUIRED.getMessage());
        }

        // Fetch the user from the database by email
        User user = userRepository.findByEmail(email.toLowerCase()).orElseThrow(() -> new RuntimeException("No account found with this email address!"));

        // Check if the user is verified and enabled
        if (user.getVerifiedAt() != null && user.isEnabled()) {
            // Generate a token and set its expiry date to 24 hours from now
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR_OF_DAY, 24);
            Date expiryDate = calendar.getTime();
            user.setResetPasswordTokenExpiryDate(expiryDate);
            user.setResetPasswordToken(TokenGenerator.generateToken());

            // Update the user in the database
            userRepository.save(user);

            // Convert the user to DTO for sending in the email
            UserDto userDto = MappingProfile.mapToUserDto(user);

            // Send the reset password email
            emailServiceImpl.sendResetPasswordMail(user.getEmail(), CustomerEmailMessage.RESET_PASSWORD_SUBJECT.getMessage(), userDto);
        } else {
            throw new RuntimeException("No account found with this email address or account is not verified/enabled!");
        }
    }

    @Override
    public void registerUser(User userDto) throws MessagingException, RoleNotFoundException {
        log.info("Creating new user with email: {}", userDto.getEmail());
        // Fetch the corresponding UserRole entity from the database
        Set<UserRole> userRole = Collections.singleton(userDto.getUserRole());
        // Check if the provided user role is valid
        if (userRole.isEmpty()) {
            throw new RoleNotFoundException("Invalid user role: " + userRole);
        }
        // Create a new User entity with the provided details
        User toSave = User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .email(userDto.getEmail())
                .phoneNumber(userDto.getPhoneNumber())
                .roles(userRole)
                .active(Active.ACTIVE)
                .password(passwordEncoder.encode(userDto.getPassword()))// Set the user's role fetched from the database
                .isEnabled(false)
                .confirmationToken(TokenGenerator.generateToken())
                .build();

        // Save the new user entity to the database
        userRepository.save(toSave);
        // Send confirmation email to the user
        //emailServiceImpl.sendConfirmationEmail(toSave, "amal98drch@gmail.com");
    }
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    @Override
    public void changePassword(ChangePasswordDTO dto) {
        log.info("Change password for token: {}", dto.getToken());

        // Find the user by the reset token
        User user = userRepository.findByResetPasswordToken(dto.getToken())
                .orElseThrow(() -> new RuntimeException("Invalid or expired reset password token."));

        // Check if token expiry date is before the current date
        if (isTokenExpired(user.getResetPasswordTokenExpiryDate())) {
            throw new RuntimeException("The Token to reset your password has expired.");
        }

        // Update the password for the user
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        user.setResetPasswordToken(null);
        user.setResetPasswordTokenExpiryDate(null);
        userRepository.save(user);
    }

    private boolean isTokenExpired(Date expiryDate) {
        return expiryDate != null && expiryDate.before(new Date());
    }



}




