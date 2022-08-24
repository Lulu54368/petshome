package com.itproject.petshome.dto;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
@Data
@RedisHash("UserCode")
public class UserCodeDTO {
    private long userId; //the id of users
    private String verificationCode;

}
