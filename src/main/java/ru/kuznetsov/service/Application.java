package ru.kuznetsov.service;

import ru.kuznetsov.service.CBRClasses.ArrayOfDouble;
import ru.kuznetsov.service.CBRClasses.CreditInfoByIntCodeExResponse;
import ru.kuznetsov.service.CBRClasses.CreditOrgInfo;
import ru.kuznetsov.service.CBRClasses.CreditOrgInfoSoap;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args){
        CreditOrgInfo creditOrgInfo = new CreditOrgInfo();
        CreditOrgInfoSoap soap = creditOrgInfo.getCreditOrgInfoSoap();
        List<Double> internalCodes = new ArrayList<>();
        internalCodes.add(soap.bicToIntCode("040173725"));

        //System.out.println(intCode);

        ArrayOfDouble a = new ArrayOfDouble();
        a.setDoubles(internalCodes);

        CreditInfoByIntCodeExResponse.CreditInfoByIntCodeExResult info = soap.creditInfoByIntCodeEx(a);

        org.w3c.dom.Element el = (org.w3c.dom.Element)info.getAny();
    }
}
