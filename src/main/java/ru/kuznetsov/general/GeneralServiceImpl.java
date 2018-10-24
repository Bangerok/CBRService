package ru.kuznetsov.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cbr.ArrayOfDouble;
import ru.kuznetsov.service.cbr.CBRService;
import ru.kuznetsov.request.ServiceResponse;
import ru.kuznetsov.util.UtilService;

import java.io.IOException;
import java.util.List;

@Service
public class GeneralServiceImpl implements GeneralService {
    private final CBRService cbrService;

    private final UtilService utilService;

    @Autowired
    public GeneralServiceImpl(CBRService cbrService, UtilService utilService) {
        this.cbrService = cbrService;
        this.utilService = utilService;
    }

    @Override
    public ServiceResponse processingData(String stringXML) throws IOException {
        ServiceResponse response = new ServiceResponse();

        // Получаем из строки формата XML список bic кодов
        List bicCodesList = utilService.getDataFromXML(stringXML.substring(1, stringXML.length() - 1));

        // И дальше по bic кодам получаем внутренние коды
        ArrayOfDouble internalCodesList = cbrService.getInternalCodesFromBicCodes(bicCodesList);

        // По внутреннему коду мы можем получить всю информацию об организации
        List creditOrgInfoList = cbrService.getCreditOrgInfoList(internalCodesList);

        // Создание PDF файла из данных организаций и получение пути до этого файла
        String filiePathToPDF = utilService.createPDFFileFromCBRdata(creditOrgInfoList);

        return response;
    }
}
