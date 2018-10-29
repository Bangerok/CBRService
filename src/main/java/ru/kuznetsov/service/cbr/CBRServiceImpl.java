package ru.kuznetsov.service.cbr;

import org.springframework.stereotype.Service;
import ru.cbr.*;

import java.util.List;

@Service
public class CBRServiceImpl implements CBRService{
    private CreditOrgInfoSoap soap;

    /**
     * Конструктор CBRServiceImpl, в котором инициализируется объект типа CreditOrgInfoSoap
     */
    public CBRServiceImpl() throws Exception {
        try {
            initCreditOrgInfoSoap();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
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

            if (internalCode == -1) {
                continue;
            }

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
        CreditInfoByIntCodeExXMLResponse.CreditInfoByIntCodeExXMLResult info;
        info = soap.creditInfoByIntCodeExXML(internalCodes);

        return ((CreditOrgInfo) info.getContent().get(0)).getCOSAndLICS();
    }

    /**
     * Инициализация объекта для работы с CBR.ru методами
     */
    public void initCreditOrgInfoSoap() throws Exception {
        try {
            this.soap = new CreditOrgInfo_Service().getCreditOrgInfoSoap();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
