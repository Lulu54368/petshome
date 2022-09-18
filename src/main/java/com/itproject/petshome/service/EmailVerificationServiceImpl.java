package com.itproject.petshome.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
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

import static com.sun.mail.imap.SortTerm.TO;

@Service
@Setter
@Getter
@AllArgsConstructor
public class EmailVerificationServiceImpl implements EmailService {

    private UserCodeRepository userCodeRepository;



    private JavaMailSender sender;

    @Override
    public void sendEmail(User user, String siteURL) throws MessagingException, UserCodeNotFoundException {

        final String FROM = "petshome@cute-lulu.com";




        // The subject line for the email.
        final String SUBJECT = "Verify your email";

        String TO = user.getEmail();

        // The email body for recipients with non-HTML email clients.
        final String TEXTBODY = "Please verify your email"+siteURL;



        try {
                AmazonSimpleEmailService client =
                        AmazonSimpleEmailServiceClientBuilder.standard()
                                // Replace US_WEST_2 with the AWS Region you're using for
                                // Amazon SES.
                                .withRegion(Regions.AP_SOUTHEAST_2).build();
                SendEmailRequest request = new SendEmailRequest()
                        .withDestination(
                                new Destination().withToAddresses(TO))
                        .withMessage(new Message()
                                .withBody(new Body()

                                        .withText(new Content()
                                                .withCharset("UTF-8").withData(TEXTBODY)))
                                .withSubject(new Content()
                                        .withCharset("UTF-8").withData(SUBJECT)))
                        .withSource(FROM);
                client.sendEmail(request);
                System.out.println("Email sent!");
            } catch (Exception ex) {
                System.out.println("The email was not sent. Error message: "
                        + ex.getMessage());
            }
        }




}