package com.packsendme.roadbrewa.roadway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class Roadway_Application {

	public static void main(String[] args) {
		SpringApplication.run(Roadway_Application.class, args);
	}
}

