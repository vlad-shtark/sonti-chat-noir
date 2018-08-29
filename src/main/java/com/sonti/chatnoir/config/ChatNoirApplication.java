package com.sonti.chatnoir.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.sonti.chatnoir")
public class ChatNoirApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatNoirApplication.class, args);
    }

}
