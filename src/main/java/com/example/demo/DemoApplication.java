package com.example.demo;
import java.math.BigInteger;
import java.security.MessageDigest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.NoSuchAlgorithmException;
import java.security.Security;

@SpringBootApplication(scanBasePackages = "com.example.demo.controller")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    String index(){

        return "hello";
    }
}
