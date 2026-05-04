package com.kiranaos.kiranaos_billing_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class KiranaosBillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KiranaosBillingServiceApplication.class, args);
    }

}
