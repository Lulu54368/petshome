package com.itproject.petshome.config;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
@Data
public class AWSProperties {

    @Value("${aws.s3.bucket.name}")
    private String bucketName;
    @Value("${aws.s3.access.key}")
    private String s3_access_key;
    @Value("${aws.s3.secret.key}")
    private String s3_secret_key;
}
