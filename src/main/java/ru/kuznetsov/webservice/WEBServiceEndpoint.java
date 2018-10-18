package ru.kuznetsov.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import ru.kuznetsov.service.CBRService;
import ru.kuznetsov.service.CBRServiceImpl;
import ru.kuznetsov.soap.ServiceRequest;
import ru.kuznetsov.soap.ServiceResponse;

@Endpoint
public class WEBServiceEndpoint {
    private static final String NAMESPACE_URI = "http://www.kuznetsov.ru/WEBService";
    private CBRService service;

    @Autowired
    public void HelloService (CBRServiceImpl service) {
        this.service = service;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ServiceRequest")
    public ServiceResponse getCreditInfoSoap(ServiceRequest request) throws Exception {
        String bicCodes = request.getBicCode();
        ServiceResponse resp = new ServiceResponse();

        resp.setTypeResponse("ok");
        resp.setAdditionalInfo("test");
        resp.setFile("test.pdf success");
        return resp;
    }
}