package com.packsendme.roadway.businessrule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BusinessRules_Application {

	public static void main(String[] args) {
		SpringApplication.run(BusinessRules_Application.class, args);
	}
}

