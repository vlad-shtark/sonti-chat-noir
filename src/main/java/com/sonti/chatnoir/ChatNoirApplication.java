package com.sonti.chatnoir;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ChatNoirApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatNoirApplication.class, args);
        log.info("Logger initialised");
    }

}
