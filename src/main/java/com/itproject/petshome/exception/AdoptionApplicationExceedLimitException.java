package com.itproject.petshome.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BANDWIDTH_LIMIT_EXCEEDED, reason = "exceed adoption application limitation")
public class AdoptionApplicationExceedLimitException extends Exception{
}
