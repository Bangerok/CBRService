package ru.kuznetsov.service.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.kuznetsov.service.general.GeneralService;
import ru.kuznetsov.request.ServiceRequest;
import ru.kuznetsov.request.ServiceResponse;

@Endpoint
public class SoapServiceEndpoint {
    private static final String NAMESPACE_URI = "http://www.kuznetsov.ru/request";

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
        // Берем строку из запроса, которая имеет формат XML
        String stringXML = request.getBicCodesXML();

        // Формируем ответ с помощью метода и возвращаем его клиенту
        return generalService.processingData(stringXML);
    }
}