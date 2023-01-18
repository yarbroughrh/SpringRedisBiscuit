package com.biscuitswithjelly.biscuit;
import com.clevercloud.biscuit.crypto.KeyPair;
import com.clevercloud.biscuit.datalog.AuthorizedWorld;
import com.clevercloud.biscuit.error.Error;
import com.clevercloud.biscuit.token.Authorizer;
import com.clevercloud.biscuit.token.Biscuit;
import com.clevercloud.biscuit.token.builder.Block;
import io.vavr.Tuple2;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
public class Auth {
    public KeyPair root() {
        return new KeyPair();
    }
    public Biscuit createToken(KeyPair root) throws Error {
        return Biscuit.builder(root)
                .add_authority_fact("user(\"1234\")")
                .add_authority_check("check if operation(\"read\")")
                .build();
    }
    public Tuple2<Long, AuthorizedWorld> authorize(KeyPair root, byte[] serializedToken) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, Error {
//        if (!role.startsWith("biscuit:")) {
//            //add code to look for biscuit:

        return Biscuit.from_bytes(serializedToken, root.public_key()).authorizer()
                .add_fact("resource(\"/folder1/file1\")")
                .add_fact("operation(\"read\")")
                .allow()
                .authorize();
    }
    public Biscuit attenuate(KeyPair root, byte[] serializedToken) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, Error {
        Biscuit token = Biscuit.from_bytes(serializedToken, root.public_key());
        Block block = token.create_block().add_check("check if operation(\"read\")");
        return token.attenuate(block);
    }
}
