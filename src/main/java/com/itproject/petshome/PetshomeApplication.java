package com.itproject.petshome;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Pet's home API", version = "1.0"))
@ConfigurationPropertiesScan("com.itproject.petshome.config")
@EnableCaching
public class PetshomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetshomeApplication.class, args);
	}

}
