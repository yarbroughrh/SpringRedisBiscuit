package com.biscuitswithjelly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringBootRedisBiscuitApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRedisBiscuitApplication.class, args);
	}

}
