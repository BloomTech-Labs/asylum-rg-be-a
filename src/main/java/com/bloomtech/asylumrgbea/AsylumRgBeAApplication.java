package com.bloomtech.asylumrgbea;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@EnableDynamoDBRepositories
@SpringBootApplication
public class AsylumRgBeAApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsylumRgBeAApplication.class, args);
    }
}
