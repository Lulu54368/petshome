package com.itproject.petshome.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CREATED, reason = "volunteer application already exist in database")
public class VolunteerApplicationAlreadyExistException extends Exception{
}
