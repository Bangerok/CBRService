package ru.kuznetsov.service.general;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cbr.ArrayOfDouble;
import ru.kuznetsov.request.ServiceResponse;
import ru.kuznetsov.service.cbr.CBRService;
import ru.kuznetsov.service.util.UtilService;

import java.util.List;

@Service
public class GeneralServiceImpl implements GeneralService {
    private static final Logger logger = Logger.getLogger(GeneralServiceImpl.class);

    private final CBRService cbrService;
    private final UtilService utilService;

    @Autowired
    public GeneralServiceImpl(CBRService cbrService, UtilService utilService) {
        this.cbrService = cbrService;
        this.utilService = utilService;
    }

    @Override
    public ServiceResponse processingData(String stringXML) {
        ServiceResponse response = new ServiceResponse();
        response.setTypeResponse("Ok");
        response.setAdditionalInfo("All done!");
        logger.info("Test Logger");

        try {
            // Получаем из строки формата XML список bic кодов
            List bicCodesList = utilService.getDataFromXML(stringXML.trim().replaceFirst("^([\\W]+)<","<"));
            if (bicCodesList.size() == 0) {
                response.setTypeResponse("Warning");
                response.setAdditionalInfo("Received BIC codes from XML - 0.");

                return response;
            }

            // И дальше по bic кодам получаем внутренние коды
            ArrayOfDouble internalCodesList = cbrService.getInternalCodesFromBicCodes(bicCodesList);
            if (internalCodesList.getDoubles().size() < bicCodesList.size()) {
                int countInternalCodes = bicCodesList.size() - internalCodesList.getDoubles().size();
                response.setTypeResponse("Warning");
                response.setAdditionalInfo("Not found BIC codes in CBR database - " + countInternalCodes + ". Check for correct input!");

                if (internalCodesList.getDoubles().size() == 0) {
                    response.setTypeResponse("Warning/Error");
                    return response;
                }
            }

            // По внутренним кодам мы можем получить всю информацию об организациях
            List creditOrgInfoList = cbrService.getCreditOrgInfoList(internalCodesList);

            // Создание PDF файла из данных организаций и получение пути до этого файла
            String filePathToPDF = utilService.createPDFFileFromCBRdata(creditOrgInfoList);

            // Получение из файла строки, содержание которой будет base64
            String stringBase64 = utilService.getBase64FromPDF(filePathToPDF);

            // Удаление файла
            utilService.deleteFile(filePathToPDF);

            // Отправка ответа
            response.setFile(stringBase64);

            return response;
        } catch (Exception e) {
            response.setTypeResponse("Error");
            response.setAdditionalInfo(e.getMessage());

            return response;
        }
    }
}
