package com.ecoorbit.ecoorbit_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EcoorbitApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcoorbitApiApplication.class, args);
	}

}
