package ru.kuznetsov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    /**
     * Запускает сервер spring-boot
     */
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}