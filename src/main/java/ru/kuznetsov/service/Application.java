package ru.kuznetsov.service;

import ru.kuznetsov.service.CBRClasses.*;

public class Application {
    public static void main(String[] args){
        CreditOrgInfo creditOrgInfo = new CreditOrgInfo();
        CreditOrgInfoSoap soap = creditOrgInfo.getCreditOrgInfoSoap();

        double internalCode = soap.bicToIntCode("040173725");

        ArrayOfDouble internalCodes = new ArrayOfDouble();
        internalCodes.getDoubles().add(internalCode);

        CreditInfoByIntCodeExXMLResponse.CreditInfoByIntCodeExXMLResult info = soap.creditInfoByIntCodeExXML(internalCodes);
    }
}