package ru.kuznetsov.service;

import ru.cbr.*;
import ru.kuznetsov.Application;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CBRServiceImpl implements CBRService{
    private static Logger log = Logger.getLogger(Application.class.getName());
    private CreditOrgInfoSoap soap;

    /**
     * Конструктор CBRServiceImpl, в котором инициализируется объект типа CreditOrgInfoSoap
     */
    public CBRServiceImpl() {
        initCreditOrgInfoSoap();
    }

    /**
     * Получение данных об организациях ввиде списка
     * @return List
     */
    public List getCreditOrgInfoList(){
        try {
            double internalCode = soap.bicToIntCode("040173725");

            ArrayOfDouble internalCodes = new ArrayOfDouble();
            List internalCodesList = internalCodes.getDoubles();
            internalCodesList.add(internalCode);
            internalCodesList.add((double) 10000002);
            internalCodesList.add((double) 10000003);

            CreditInfoByIntCodeExXMLResponse.CreditInfoByIntCodeExXMLResult info;
            info = soap.creditInfoByIntCodeExXML(internalCodes);
            log.log(Level.INFO, "creditInfoByIntCodeExXML returned response - true.");

            return ((CreditOrgInfo) info.getContent().get(0)).getCOSAndLICS();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Exception - " + e.getMessage());
            return null;
        }
    }

    /**
     * Инициализация объекта для работы с CBR.ru методами
     */
    public void initCreditOrgInfoSoap(){
        try {
            this.soap = new CreditOrgInfo_Service().getCreditOrgInfoSoap();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Exception [initCreditOrgInfoSoap] - " + e.getMessage());
        }
    }
}
