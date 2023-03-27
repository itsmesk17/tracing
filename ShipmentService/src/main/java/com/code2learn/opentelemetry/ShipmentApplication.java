package com.code2learn.opentelemetry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.code2learn.log4j2tracing.configuration", "com.code2learn.opentelemetry"})
@EnableTransactionManagement
public class ShipmentApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShipmentApplication.class, args);
    }
}
