package ru.kuznetsov.service;

import ru.kuznetsov.service.CBRClasses.ArrayOfDouble;
import ru.kuznetsov.service.CBRClasses.CreditInfoByIntCodeExXMLResponse;
import ru.kuznetsov.service.CBRClasses.CreditOrgInfo;
import ru.kuznetsov.service.CBRClasses.CreditOrgInfoSoap;

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
