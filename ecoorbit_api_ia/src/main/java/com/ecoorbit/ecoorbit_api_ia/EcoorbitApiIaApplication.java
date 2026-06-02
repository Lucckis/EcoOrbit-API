package com.ecoorbit.ecoorbit_api_ia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EcoorbitApiIaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcoorbitApiIaApplication.class, args);
	}

}
