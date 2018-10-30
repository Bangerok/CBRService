package ru.kuznetsov;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

/** Класс, инициализирующий запуск сервиса для SOAP и JMS взаимодействия
 * @author Kuznetsov Vladislav
 * @version 1.8.0
 */
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