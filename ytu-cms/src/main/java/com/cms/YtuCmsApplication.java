package com.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class YtuCmsApplication {

	public static void main(String[] args) {
		final String uri = "mongodb+srv://dbAdmin:qssUbEBV8Dh3cnUs@sat.6wtix.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";
		final String database = "cms";
		
		SpringApplication.run(YtuCmsApplication.class, args);
		MongoDB.startClient(database, uri);
	}

}