package com.itproject.petshome.config;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
@Data
public class AWSProperties {
    @Value("${aws.s3.bucket.name}")
    private String bucketName;
}
