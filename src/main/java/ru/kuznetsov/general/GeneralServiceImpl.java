package ru.kuznetsov.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cbr.ArrayOfDouble;
import ru.kuznetsov.request.ServiceResponse;
import ru.kuznetsov.service.cbr.CBRService;
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
        if (bicCodesList == null) {
            response.setTypeResponse("Error");
            response.setAdditionalInfo("Bad XML has come");

            return response;
        }

        // И дальше по bic кодам получаем внутренние коды
        ArrayOfDouble internalCodesList = cbrService.getInternalCodesFromBicCodes(bicCodesList);
        if (internalCodesList.getDoubles().size() < bicCodesList.size()) {
            response.setTypeResponse("Error");
            response.setAdditionalInfo("Bad XML has come");
        }

        // По внутренним кодам мы можем получить всю информацию об организациях
        List creditOrgInfoList = cbrService.getCreditOrgInfoList(internalCodesList);

        // Создание PDF файла из данных организаций и получение пути до этого файла
        String filiePathToPDF = utilService.createPDFFileFromCBRdata(creditOrgInfoList);

        // Получение из файла строки, содержание которой будет base64
        String stringBase64 = utilService.getBase64FromPDF(filiePathToPDF);

        // Сборка всех данных в ответ
        response.setTypeResponse("Ok");
        response.setAdditionalInfo("Все bic коды существующие!");
        response.setFile(stringBase64);

        return response;
    }
}
