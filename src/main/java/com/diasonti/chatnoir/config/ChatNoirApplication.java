package com.diasonti.chatnoir.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication(scanBasePackages = "com.diasonti.chatnoir")
@PropertySources({
        @PropertySource("classpath:/application.properties"),
        @PropertySource("classpath:/chat.properties")
})
public class ChatNoirApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatNoirApplication.class, args);
    }

}
