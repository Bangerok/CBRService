package ru.kuznetsov;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class Application {
    /**
     * Запускает сервер spring-boot и jms
     */
    public static void main(String[] args) {
        BasicConfigurator.configure();

        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
    }
}