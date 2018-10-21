package ru.kuznetsov.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.cbr.ArrayOfDouble;
import ru.kuznetsov.service.CBRService;
import ru.kuznetsov.soap.ServiceRequest;
import ru.kuznetsov.soap.ServiceResponse;
import ru.kuznetsov.util.UtilService;

import java.util.List;

@Endpoint
public class WEBServiceEndpoint {
    private static final String NAMESPACE_URI = "http://www.kuznetsov.ru/soap";

    private final CBRService cbrService;

    private final UtilService utilService;

    @Autowired
    public WEBServiceEndpoint(CBRService cbrService, UtilService utilService) {
        this.cbrService = cbrService;
        this.utilService = utilService;
    }

    /**
     * Получение из запроса данных и их последующая обработка для отправки ответа клиенту.
     * @param request запрос от клиента
     * @return ServiceResponse
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ServiceRequest")
    @ResponsePayload
    public ServiceResponse getCreditInfoSoap(@RequestPayload ServiceRequest request) {
        // Берем строку из запроса, которая имеет формат XML
        String stringXML = request.getBicCodesXML();

        // Получаем из нее список bic кодов
        List bicCodesList = utilService.getDataFromXML(stringXML.substring(1, stringXML.length() - 1));

        // И дальше по bic кодам получаем внутренние коды
        ArrayOfDouble internalCodesList = cbrService.getInternalCodesFromBicCodes(bicCodesList);

        // По внутреннему коду мы можем получить всю информацию об организации
        List creditOrgInfoList = cbrService.getCreditOrgInfoList(internalCodesList);

        // Формирование и отправка ответа клиенту
        ServiceResponse response = new ServiceResponse();
        response.setTypeResponse("ok");
        response.setAdditionalInfo(stringXML);
        response.setFile("test.pdf success");

        return response;
    }
}