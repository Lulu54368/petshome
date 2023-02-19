package com.itproject.petshome.service;

import com.itproject.petshome.exception.UserCodeNotFoundException;
import com.itproject.petshome.model.User;
import com.itproject.petshome.repository.UserCodeRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Setter
@Getter
@AllArgsConstructor
public class EmailVerificationServiceImpl implements EmailService {

    private UserCodeRepository userCodeRepository;



    private JavaMailSender sender;

    @Override
    public void sendEmail(User user, String siteURL) throws MessagingException, UserCodeNotFoundException {

        String toAddress = user.getEmail();

        String subject = "Please verify your registration";
        String content = "Hi there,"
                + "Please click the link below to verify your registration:  \n"
                + " \"[[URL]]\" \n"
                + "After clicking the link, please go back to the home page to login"
                + "Thank you\n"
                + "pet's home.co";

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);


        try {
            helper.setFrom("petshome@cutelulu.me");
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.setText(content);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        String userCode = userCodeRepository
                .findById(user.getId())
                .getVerificationCode();
        String verifyURL = siteURL + "/api/v1/auth/verify?code=" + userCode;

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content);


        sender.send(message);
    }



}