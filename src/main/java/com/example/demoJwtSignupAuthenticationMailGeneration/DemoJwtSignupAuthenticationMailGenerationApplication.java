package com.example.demoJwtSignupAuthenticationMailGeneration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoJwtSignupAuthenticationMailGenerationApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoJwtSignupAuthenticationMailGenerationApplication.class, args);
	}

}
