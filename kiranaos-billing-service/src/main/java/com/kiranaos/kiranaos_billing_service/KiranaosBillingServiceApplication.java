package com.kiranaos.kiranaos_billing_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableFeignClients
@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class KiranaosBillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KiranaosBillingServiceApplication.class, args);
    }

}
