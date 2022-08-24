package com.itproject.petshome.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
@RedisHash("UserCode")
@Getter
@Setter
public class UserCodeDTO {
    private long userId; //the id of users
    private String verificationCode;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
