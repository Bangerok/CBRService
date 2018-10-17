package ru.kuznetsov.service;

import ru.kuznetsov.service.CBRClasses.*;
import java.util.logging.*;

public class Application {
    private static Logger log = Logger.getLogger(Application.class.getName());

    public static void main(String[] args){
        try {
            CreditOrgInfo_Service creditOrgInfo = new CreditOrgInfo_Service();
            CreditOrgInfoSoap soap = creditOrgInfo.getCreditOrgInfoSoap();
            log.log(Level.INFO, "CreditOrgInfoSoap initialization was successful.");

            double internalCode = soap.bicToIntCode("040173725");
            log.log(Level.INFO, "bicToIntCode returned internal code - " + Double.toString(internalCode));

            ArrayOfDouble internalCodes = new ArrayOfDouble();
            internalCodes.getDoubles().add(internalCode);

            CreditInfoByIntCodeExXMLResponse.CreditInfoByIntCodeExXMLResult info;
            info = soap.creditInfoByIntCodeExXML(internalCodes);
            log.log(Level.INFO, "creditInfoByIntCodeExXML returned data - " + info.getContent().toString());
        } catch (Exception e) {
            log.log(Level.SEVERE, "Exception - " + e.getMessage());
        }
    }
}