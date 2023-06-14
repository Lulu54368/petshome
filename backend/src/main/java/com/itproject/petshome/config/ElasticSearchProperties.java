package com.itproject.petshome.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
@Data
public class ElasticSearchProperties {
    @Value("${ELASTICSEARCH_USERNAME}")
    private String username;
    @Value("${ELASTICSEARCH_PWD}")
    private String password;
}
