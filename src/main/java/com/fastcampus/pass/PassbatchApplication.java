package com.fastcampus.pass;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PassbatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(PassbatchApplication.class, args);
    }

}
