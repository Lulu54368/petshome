package com.itproject.petshome.config;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.BucketAccelerateConfiguration;
import com.amazonaws.services.s3.model.BucketAccelerateStatus;
import com.amazonaws.services.s3.model.GetBucketAccelerateConfigurationRequest;
import com.amazonaws.services.s3.model.SetBucketAccelerateConfigurationRequest;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
public class AWSConfig {
    private AWSProperties awsProperties;
    private  static  final Logger logger = LoggerFactory.getLogger(AWSConfig.class);

    @Bean
    public AmazonS3  s3Client(){
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(
                awsProperties.getS3_access_key(),
                awsProperties.getS3_secret_key());
        Regions clientRegion = Regions.AP_SOUTHEAST_2;
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(clientRegion)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
        // Enable Transfer Acceleration for the specified bucket.
        s3Client.setBucketAccelerateConfiguration(
                new SetBucketAccelerateConfigurationRequest(awsProperties.getBucketName(),
                        new BucketAccelerateConfiguration(
                                BucketAccelerateStatus.Enabled)));

        // Verify that transfer acceleration is enabled for the bucket.
        String accelerateStatus = s3Client.getBucketAccelerateConfiguration(
                        new GetBucketAccelerateConfigurationRequest(awsProperties.getBucketName()))
                .getStatus();
        logger.info("Bucket accelerate status: " + accelerateStatus);
        return s3Client;
    }

}
