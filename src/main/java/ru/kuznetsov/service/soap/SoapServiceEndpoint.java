package ru.kuznetsov.service.soap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.kuznetsov.service.general.GeneralService;
import ru.kuznetsov.request.ServiceRequest;
import ru.kuznetsov.request.ServiceResponse;

/** Класс, отвечающий за прием SOAP запросов
 * @author Kuznetsov Vladislav
 * @version 2.0.0
 */
@Endpoint
public class SoapServiceEndpoint {
    private static final Logger logger = Logger.getLogger(SoapServiceEndpoint.class);
    private static final String NAMESPACE_URI = "http://www.kuznetsov.ru/request";

    private final GeneralService generalService;

    /**
     * Конструктор "GeneralServiceImpl", в котором инициализируются объекты "generalService" типа "GeneralService"
     */
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
        logger.info("SOAP запрос получен");

        // Берем строку из запроса, которая имеет формат XML
        String stringXML = request.getBicCodesXML();
        logger.info("Пришла строка - " + stringXML);

        // Формируем ответ с помощью метода и возвращаем его клиенту
        ServiceResponse response = generalService.processingData(stringXML);

        logger.info("SOAP ответ отправлен");
        return response;
    }
}