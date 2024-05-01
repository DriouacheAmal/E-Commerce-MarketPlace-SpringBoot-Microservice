package com.example.User.Email;

import com.example.User.Entity.DTO.UserDto;
import com.example.User.Entity.User;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;
@Service
@Slf4j
@AllArgsConstructor
public class EmailServiceImpl implements IEmailService{

    private JavaMailSender mailSender;

    public void sendResetPasswordMail(String recipientEmail, String subject, UserDto userDto) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(recipientEmail);
        helper.setSubject(subject);
        helper.setText("Dear " + userDto.getFirstname() + ",\n\n"
                + "Please use the following link to reset your password: "
                + "http://localhost8080/auth/resetPassword?token=" + userDto.getResetPasswordToken());
        mailSender.send(mimeMessage);
    }
    public void sendConfirmationEmail(User user, String recipientEmail) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(recipientEmail);
        helper.setSubject("Welcome to our platform!");
        helper.setText("Dear " + user.getFirstname() + ",\n\n"
                + "Thank you for registering with us. Please click the following link to confirm your email address: "
                + "http://localhost8080/auth/confirm?token=" + user.getConfirmationToken());
        mailSender.send(mimeMessage);
    }

    /*private void sendEmail(String to, String subject, String body) throws MessagingException {

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "your.smtp.host");
        properties.put("mail.smtp.port", "your.smtp.port");

        Session session = Session.getInstance(properties, null);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("amal98drch@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }

     */


}


