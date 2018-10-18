package ru.kuznetsov.webservice;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class WEBServiceEndpoint {
    private static final String NAMESPACE_URI = "http://localhost:8080/WEBService";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInfoPDF")
    @ResponsePayload
    public String getInfoPDF(@RequestPayload String request) {

        return "true";
    }
}