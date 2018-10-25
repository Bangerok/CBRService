package ru.kuznetsov.service.soap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.kuznetsov.general.GeneralService;
import ru.kuznetsov.request.ServiceRequest;
import ru.kuznetsov.request.ServiceResponse;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Endpoint
public class SoapServiceEndpoint {
    private static final String NAMESPACE_URI = "http://www.kuznetsov.ru/request";
    private static Logger logger = Logger.getLogger(SoapServiceEndpoint.class);

    private final GeneralService generalService;

    @Autowired
    public SoapServiceEndpoint(GeneralService generalService) {
        this.generalService = generalService;
    }

    /**
     * Получение из запроса данных и их последующая обработка для отправки ответа клиенту.
     * @param request запрос от клиента
     * @return ServiceResponse
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ServiceRequest")
    @ResponsePayload
    public ServiceResponse getCreditInfoSoap(@RequestPayload ServiceRequest request){
        try {
            logger.info("Request received.");

            // Берем строку из запроса, которая имеет формат XML
            String stringXML = request.getBicCodesXML();

            // Формируем ответ с помощью метода и возвращаем его клиенту
            ServiceResponse response = generalService.processingData(stringXML);

            logger.info("Response send.");
            return response;
        } catch (IOException e) {
            e.printStackTrace();

            logger.error("No valid request data.");
            return null;
        } finally {
            for (File file: Objects.requireNonNull(new File("C:\\ProgramData\\CBRService\\PDF\\").listFiles())) {
                if (file.isFile()) {
                    if (file.delete()) {
                        logger.info("PDF file - " + file.getName() + ", delete success.");
                    } else {
                        logger.error("PDF file - " + file.getName() + ", delete error.");
                    }
                }
            }
        }
    }
}