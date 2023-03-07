package com.itproject.petshome.config;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {
    @Bean
    public AmazonS3  s3Client(){
        Regions clientRegion = Regions.AP_SOUTHEAST_2;
        return AmazonS3ClientBuilder.standard()
                .withRegion(clientRegion)
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();
    }

}
