package ru.kuznetsov.service.jms;

import org.apache.activemq.Message;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import ru.kuznetsov.service.general.GeneralService;
import ru.kuznetsov.request.ServiceResponse;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/** Класс, отвечающий за получение JMS сообщений
 * @author Kuznetsov Vladislav
 * @version 2.0.1
 */
@Component
public class JMSListener {
    private static final Logger logger = Logger.getLogger(JMSListener.class);
    private final GeneralService generalService;

    /**
     * Конструктор "GeneralServiceImpl", в котором инициализируются объекты "generalService" типа "GeneralService"
     */
    @Autowired
    public JMSListener(GeneralService generalService) {
        this.generalService = generalService;
    }


    @JmsListener(destination = "inbound.queue")
    @SendTo("outbound.queue")
    public String receiveMessage(Message message) {
        try {
            logger.info("JMS запрос получен");

            String stringXML;
            ServiceResponse response;

            // Берем строку из запроса, которая имеет формат XML
            TextMessage textMessage = (TextMessage) message;
            stringXML = textMessage.getText();
            logger.info("Пришла строка - " + stringXML);

            // Формируем ответ с помощью метода и возвращаем его клиенту
            response = generalService.processingData(stringXML);

            logger.info("JMS ответ отправлен");
            return response.getFile();
        } catch (JMSException e) {
            logger.error(e.getMessage());

            return e.getMessage();
        }
    }

}
