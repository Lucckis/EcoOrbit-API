package com.ecoorbit.ecoorbit_api_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EcoorbitApiServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcoorbitApiServerApplication.class, args);
	}

}
