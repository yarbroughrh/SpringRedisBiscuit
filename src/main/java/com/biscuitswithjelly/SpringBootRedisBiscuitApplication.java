package com.biscuitswithjelly;
import ch.qos.logback.core.subst.Token;
import com.biscuitswithjelly.biscuit.Auth;
import com.clevercloud.biscuit.crypto.KeyPair;
import com.clevercloud.biscuit.datalog.AuthorizedWorld;
import com.clevercloud.biscuit.error.Error;
import com.clevercloud.biscuit.token.Biscuit;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import io.vavr.Tuple2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

@SpringBootApplication
@RestController
public class SpringBootRedisBiscuitApplication {

	public static void main(String[] args) throws Error, NoSuchAlgorithmException, SignatureException, InvalidKeyException {

		SpringApplication.run(SpringBootRedisBiscuitApplication.class, args);
		Auth auth = new Auth();
		KeyPair userKeyPair = auth.root();
		long userId = 1234;
		long blogId = 4321;
		System.out.println();

		// creates a new biscuit token
		Biscuit token = null;
		try {
			token = auth.createToken(userKeyPair, userId, blogId);
		} catch (Error e) {
			throw new RuntimeException(e);
		}
		// calls the authorize method of the Auth class and passing the userKeyPair, token.serialize(), userId, and blogId
		// as arguments to check if the user has permission to access the blog
		System.out.println(token);
		Tuple2<Long, AuthorizedWorld> result = auth.authorize(userKeyPair, token.serialize(), userId, blogId);
		if (result._1 == 0) {
			System.out.println("User has permission to access blog");
			System.out.println(token.print());
		} else {
			System.out.println("User does not have permission to access blog");
			token = auth.attenuate(userKeyPair, token.serialize());
			System.out.println(token);
			System.out.println("Permission removed");
		}
	}



}
