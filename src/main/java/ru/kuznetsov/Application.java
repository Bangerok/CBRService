package ru.kuznetsov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

import java.util.Collections;

@SpringBootApplication
@EnableJms
public class Application {
    /**
     * Запускает сервер spring-boot и jms
     */
    public static void main(String[] args){
        SpringApplication app = new SpringApplication(Application.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "3000"));
        /*app.setDefaultProperties(Collections.singletonMap("spring.activemq.broker-url", "tcp://localhost:61616"));
        app.setDefaultProperties(Collections.singletonMap("spring.activemq.user", "admin"));
        app.setDefaultProperties(Collections.singletonMap("spring.activemq.password", "admin"));*/
        app.run(args);
    }
}