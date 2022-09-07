package com.itproject.petshome.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CREATED, reason = "The user is not allowed to adopt more than 2 pets")
public class AdoptionExceedsMaxPetsAllowed extends Exception{
}
