package com.itproject.petshome.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "fail to create the pet profile")
public class PetCreationFailure extends Exception{
}
