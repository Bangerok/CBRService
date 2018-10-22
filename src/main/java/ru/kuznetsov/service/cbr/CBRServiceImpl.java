package ru.kuznetsov.service.cbr;

import org.springframework.stereotype.Service;
import ru.cbr.*;
import ru.kuznetsov.Application;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
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
     * Получение списка внутренних кодов из списка бик кодов
     * @param bicCodesList список бик кодов организации
     * @return ArrayOfDouble
     */
    @Override
    public ArrayOfDouble getInternalCodesFromBicCodes(List bicCodesList) {
        ArrayOfDouble internalCodes = new ArrayOfDouble();
        List<Double> internalCodesList = internalCodes.getDoubles();
        for (Object aBicCodesList : bicCodesList) {
            String bicCode = (String) aBicCodesList;
            double internalCode = soap.bicToIntCode(bicCode);
            internalCodesList.add(internalCode);
        }

        return internalCodes;
    }
    /**
     * Получение данных об организациях в виде списка,
     * используя внутренний код, полученный по bic коду
     * @param internalCodes список внутренних кодов
     * @return List
     */
    public List getCreditOrgInfoList(ArrayOfDouble internalCodes){
        try {
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
