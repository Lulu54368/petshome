package com.itproject.petshome.config;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
@Data
public class AWSProperties {

    Dotenv dotenv = Dotenv
            .configure()
            .directory("backend/")
            .load();
    @Value("${aws.s3.bucket.name}")
    private String bucketName;
    private String s3_access_key = dotenv.get("AWS_S3_ACCESS_KEY");

    private String s3_secret_key = dotenv.get("AWS_S3_SECRET_KEY");
}
