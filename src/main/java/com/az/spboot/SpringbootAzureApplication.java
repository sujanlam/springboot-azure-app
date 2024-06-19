package com.az.spboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringbootAzureApplication {

	@GetMapping("/msg")
	public String getMessage(){
		return "This is the first part of the message!!! Also the second part!!! Third???";
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootAzureApplication.class, args);
	}

}
