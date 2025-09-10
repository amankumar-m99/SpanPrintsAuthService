package com.spanprints.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpanPrintsAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpanPrintsAuthServiceApplication.class, args);
	}

}
