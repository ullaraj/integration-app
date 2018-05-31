package com.ig.integration.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
@ComponentScan
public class IntegrationAppApplication {



    public static void main(String[] args) {
        SpringApplication.run(IntegrationAppApplication.class, args);

    }
}
