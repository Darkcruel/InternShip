package com.sgva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SgvaChatbotApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SgvaChatbotApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SgvaChatbotApplication.class);
	}

}
