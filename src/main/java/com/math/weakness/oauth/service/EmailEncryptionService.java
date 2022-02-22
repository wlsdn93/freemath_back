package com.math.weakness.oauth.service;


import java.math.BigInteger;
import java.security.MessageDigest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailEncryptionService {

    @Value("${encrypt.salt}")
    private String salt;

    public String encryptSHA256(String email) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        md.update(salt.getBytes());
        md.update(email.getBytes());

        return String.format("%064x", new BigInteger(1, md.digest()));

    }

}
