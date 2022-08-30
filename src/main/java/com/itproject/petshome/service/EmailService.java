package com.itproject.petshome.service;

import com.itproject.petshome.exception.UserCodeNotFoundException;
import com.itproject.petshome.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

public interface EmailService {

    void sendEmail(User user, String siteUrl) throws MessagingException, UserCodeNotFoundException;
}
