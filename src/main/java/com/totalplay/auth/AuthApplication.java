package com.totalplay.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.totalplay.auth")
public class AuthApplication {

	public static void main(String[] args) {		
		SpringApplication.run(AuthApplication.class, args);
	}

}
