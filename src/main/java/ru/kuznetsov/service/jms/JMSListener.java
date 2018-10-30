package ru.kuznetsov.service.jms;

import org.apache.activemq.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import ru.kuznetsov.general.GeneralService;
import ru.kuznetsov.request.ServiceResponse;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class JMSListener {
    private final GeneralService generalService;

    @Autowired
    public JMSListener(GeneralService generalService) {
        this.generalService = generalService;
    }

    @JmsListener(destination = "inbound.queue")
    @SendTo("outbound.queue")
    public String receiveMessage(Message message) {
        try {
            String stringXML;
            ServiceResponse response;

            // Берем строку из запроса, которая имеет формат XML
            TextMessage textMessage = (TextMessage) message;
            stringXML = textMessage.getText();

            // Формируем ответ с помощью метода и возвращаем его клиенту
            response = generalService.processingData(stringXML);

            return response.getFile();
        } catch (JMSException e) {
            return null;
        }
    }

}
