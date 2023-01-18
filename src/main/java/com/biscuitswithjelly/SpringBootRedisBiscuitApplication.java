package com.biscuitswithjelly;

import com.biscuitswithjelly.biscuit.Auth;
import com.clevercloud.biscuit.crypto.KeyPair;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import java.io.*;
@SpringBootApplication
@RestController
public class SpringBootRedisBiscuitApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(SpringBootRedisBiscuitApplication.class, args);
		Auth auth = new Auth();
		KeyPair root = auth.root();
		System.out.println(root);




	}



}
