package com.itproject.petshome.config;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@AllArgsConstructor
@Configuration
public class AWSConfig {
    private AWSProperties awsProperties;

    @Bean
    public AmazonS3  s3Client(){
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(
                awsProperties.getS3_access_key(),
                awsProperties.getS3_secret_key());
        Regions clientRegion = Regions.AP_SOUTHEAST_2;
        return AmazonS3ClientBuilder.standard()
                .withRegion(clientRegion)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

}
