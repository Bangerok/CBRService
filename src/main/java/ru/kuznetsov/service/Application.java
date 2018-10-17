package ru.kuznetsov.service;

import ru.kuznetsov.service.CBRClasses.*;

import javax.xml.ws.ProtocolException;

public class Application {
    public static void main(String[] args){
        try {
            CreditOrgInfo_Service creditOrgInfo = new CreditOrgInfo_Service();
            CreditOrgInfoSoap soap = creditOrgInfo.getCreditOrgInfoSoap();

            double internalCode = soap.bicToIntCode("040173725");

            ArrayOfDouble internalCodes = new ArrayOfDouble();
            internalCodes.getDoubles().add(internalCode);

            CreditInfoByIntCodeExXMLResponse.CreditInfoByIntCodeExXMLResult info;
            info = soap.creditInfoByIntCodeExXML(internalCodes);

            System.out.println("Request success!" + info.getContent().toString());
        } catch (ProtocolException e) {
            e.printStackTrace();
            System.out.println("Request error!");
        }
    }
}