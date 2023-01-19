package com.biscuitswithjelly.biscuit;
import com.biscuitswithjelly.model.Blog;
import com.clevercloud.biscuit.crypto.KeyPair;
import com.clevercloud.biscuit.datalog.AuthorizedWorld;
import com.clevercloud.biscuit.error.Error;
import com.clevercloud.biscuit.token.Authorizer;
import com.clevercloud.biscuit.token.Biscuit;
import com.clevercloud.biscuit.token.builder.Block;
import io.vavr.Tuple2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
public class Auth {

    // represents a pair of private and public keys
    public KeyPair root() {
        return new KeyPair();
    }
    // Biscuit token createToken method takes a KeyPair object
    // the add_authority_fact method is used to add facts to the token
    // check if the operation is read, then constructing the token
    public Biscuit createToken(KeyPair root, long userId, long blogId) throws Error {
        return Biscuit.builder(root)
              //.add_authority_fact("endpoint(\"users/1234/4321\")")
                .add_authority_fact("user(\"1234\")")
                .add_authority_fact("blogId(\"4321\")")
                .add_authority_check("check if operation(\"read\")")
                .build();
    }
    // The authorize method takes a KeyPair object, a serializedToken(a byte array representing the token), a userId and a blogId
    // The Biscuit.from_bytes(serializedToken, root.public_key()) method deserializes the token passed as an argument and verifies its signature using the public key of the root key pair
    // The add_fact method is used to add facts to the Authorizer,these facts represent the resource and the operation that the token is being used for
    // The allow() method is used to indicate that the token should be authorized as long as the facts added to the Authorizer match the facts in the token
    // The authorize() method is called to authorize the token. It returns a tuple where the first element is an Integer which indicates if the token is authorized or not
    public Tuple2<Long, AuthorizedWorld> authorize(KeyPair root, byte[] serializedToken, long userId, long blogId) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, Error {
        return Biscuit.from_bytes(serializedToken, root.public_key()).authorizer()
              //.add_fact("endpoint(\"users/1234/4321\")")
                .add_fact("resource(\"4321\")")
                .add_fact("operation(\"read\")")
                .allow()
                .authorize();
    }

    // The attenuate method takes a KeyPair object and a serializedToken (a byte array representing the token) as arguments
    // The Biscuit.from_bytes(serializedToken, root.public_key()) method deserializes the token passed as an argument and verifies its signature using the public key of the root key pair
    // The create_block() method creates a new Block object that can be used to attenuate the token
    // The add_check method is used to add a check to the Block, "check if operation("read")"
    // The attenuate method is called with the block as an argument. It creates a new token based on the original token, but with the added check.
    public Biscuit attenuate(KeyPair root, byte[] serializedToken) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, Error {
        Biscuit token = Biscuit.from_bytes(serializedToken, root.public_key());
        Block block = token.create_block().add_check("check if operation(\"read\")");
        return token.attenuate(block);
    }

}

