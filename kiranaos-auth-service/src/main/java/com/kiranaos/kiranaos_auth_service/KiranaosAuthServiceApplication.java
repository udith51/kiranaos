package com.kiranaos.kiranaos_auth_service;

import com.kiranaos.kiranaos_auth_service.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class KiranaosAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KiranaosAuthServiceApplication.class, args);
	}

}
