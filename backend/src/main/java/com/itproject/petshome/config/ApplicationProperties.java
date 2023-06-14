package com.itproject.petshome.config;

import com.itproject.petshome.filter.JwtTokenFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@ConfigurationProperties(prefix = "application")
@Data
public class ApplicationProperties {

    private String allowedOrigins;


    private Integer accessTokenValiditySeconds;

    private String jwtSigningKey;

}