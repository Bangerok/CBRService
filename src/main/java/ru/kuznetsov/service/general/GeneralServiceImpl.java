package ru.kuznetsov.service.general;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cbr.ArrayOfDouble;
import ru.kuznetsov.request.ServiceResponse;
import ru.kuznetsov.service.cbr.CBRService;
import ru.kuznetsov.service.util.UtilService;

import java.util.List;

/** Сервисный класс, отвечающий за связь между утилитным классом и классом работы с cbr
 * @author Kuznetsov Vladislav
 * @version 2.0.0
 */
@Service
public class GeneralServiceImpl implements GeneralService {
    private static final Logger logger = Logger.getLogger(GeneralServiceImpl.class);

    private final CBRService cbrService;
    private final UtilService utilService;

    /**
     * Конструктор "GeneralServiceImpl", в котором инициализируются объекты "cbrService" типа "CBRService" и "utilService" типа "UtilService"
     */
    @Autowired
    public GeneralServiceImpl(CBRService cbrService, UtilService utilService) {
        this.cbrService = cbrService;
        this.utilService = utilService;
    }

    @Override
    public ServiceResponse processingData(String stringXML) {
        logger.info("Обработка данных запроса началась");

        ServiceResponse response = new ServiceResponse();
        response.setTypeResponse("Ok");
        response.setAdditionalInfo("All done!");

        try {
            // Получаем из строки формата XML список bic кодов
            List bicCodesList = utilService.getDataFromXML(stringXML.trim().replaceFirst("^([\\W]+)<","<"));
            if (bicCodesList.size() == 0) {
                response.setTypeResponse("Warning");
                response.setAdditionalInfo("Received BIC codes from XML - 0.");

                return response;
            }
            logger.info("BIC коды получены");

            // И дальше по bic кодам получаем внутренние коды
            ArrayOfDouble internalCodesList = cbrService.getInternalCodesFromBicCodes(bicCodesList);
            if (internalCodesList.getDoubles().size() < bicCodesList.size()) {
                int countInternalCodes = bicCodesList.size() - internalCodesList.getDoubles().size();
                response.setTypeResponse("Warning");
                response.setAdditionalInfo("Not found BIC codes in CBR database - " + countInternalCodes + ". Check for correct input!");

                if (internalCodesList.getDoubles().size() == 0) {
                    response.setTypeResponse("Warning/Error");
                    logger.warn("Не найдены внутренние коды по BIC кодам.");

                    return response;
                }

                logger.warn("Не найдено BIC кодов в базе CBR - " + countInternalCodes);
            }

            // По внутренним кодам мы можем получить всю информацию об организациях
            List creditOrgInfoList = cbrService.getCreditOrgInfoList(internalCodesList);
            logger.info("Информация по организация получена");

            // Создание PDF файла из данных организаций и получение пути до этого файла
            String filePathToPDF = utilService.createPDFFileFromCBRdata(creditOrgInfoList);
            logger.info("PDF файл создан");

            // Получение из файла строки, содержание которой будет base64
            String stringBase64 = utilService.getBase64FromPDF(filePathToPDF);
            logger.info("Получена строка вида base64 из PDF файла");

            // Удаление файла
            utilService.deleteFile(filePathToPDF);
            logger.info("Временная папка очищена");

            // Отправка ответа
            response.setFile(stringBase64);

            return response;
        } catch (Exception e) {
            response.setTypeResponse("Error");
            response.setAdditionalInfo(e.getMessage());
            logger.error(e.getMessage());

            return response;
        } finally {
            logger.info("Ответ сформирован");
        }
    }
}
