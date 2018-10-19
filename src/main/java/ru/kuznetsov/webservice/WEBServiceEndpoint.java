package ru.kuznetsov.webservice;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.kuznetsov.soap.ServiceRequest;
import ru.kuznetsov.soap.ServiceResponse;

@Endpoint
public class WEBServiceEndpoint {
    private static final String NAMESPACE_URI = "http://localhost:8080/WEBService";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ServiceRequest")
    @ResponsePayload
    public ServiceResponse getCreditInfoSoap(@RequestPayload ServiceRequest request) {
        String bicCodes = request.getBicCode();
        ServiceResponse resp = new ServiceResponse();

        resp.setTypeResponse("ok");
        resp.setAdditionalInfo(bicCodes);
        resp.setFile("test.pdf success");
        return resp;
    }
}