package com.example.User.Email;

import com.example.User.Entity.DTO.UserDto;
import com.example.User.Entity.User;
import jakarta.mail.MessagingException;

public interface IEmailService {
    void sendConfirmationEmail(User user, String senderEmail) throws MessagingException;

    void sendResetPasswordMail(String to, String subject, UserDto userDto) throws MessagingException;
}
