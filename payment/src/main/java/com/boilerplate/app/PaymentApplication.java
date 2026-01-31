package com.boilerplate.app;

import com.boilerplate.app.base.logging.LoggingUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PaymentApplication {

    public static void main(String[] args) {
        // Initialize service name for logging
        LoggingUtil.setServiceName("service-payment");
        SpringApplication.run(PaymentApplication.class, args);
    }
}
