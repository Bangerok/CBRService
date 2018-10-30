package ru.kuznetsov.service.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ru.cbr.CreditOrgInfo;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.*;
import java.util.List;

/** Сервисный класс для методов общего назначения
 * @author Kuznetsov Vladislav
 * @version 1.8.2
 */
@Service
public class UtilServiceImpl implements UtilService{
    /**
     * Получение списка значений из конечных узлов 3 уровня из строки формата XML
     * @param stringXML формат строки - XML
     * @return ArrayOfDouble
     */
    @Override
    public List getDataFromXML(String stringXML) throws Exception {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            Document document = documentBuilder.parse(new InputSource(new StringReader(stringXML)));

            Node root = document.getDocumentElement();

            ArrayList<String> dataList = new ArrayList<>();
            NodeList bicCodes = root.getChildNodes();
            for (int i = 0; i < bicCodes.getLength(); i++) {
                Node bic = bicCodes.item(i);
                if (bic.getNodeType() != Node.TEXT_NODE) {
                    NodeList bicProps = bic.getChildNodes();
                    for(int j = 0; j < bicProps.getLength(); j++) {
                        Node bookProp = bicProps.item(j);
                        if (bookProp.getNodeType() != Node.TEXT_NODE) {
                            dataList.add(bookProp.getChildNodes().item(0).getTextContent());
                        }
                    }
                }
            }

            return dataList;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Создание PDF файла из данных организаций, полученных с CBR.ru
     * @param cbrDataList список данных по организациям
     * @return ParserDataFactory
     */
    @Override
    public String createPDFFileFromCBRdata(List cbrDataList) throws Exception {
        try {
            // Настройка PDF документа вместе с заголовком
            com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4, 50, 50, 50, 50);
            String filePath = createFilePDF();

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            // Окончание настройки и начало записи в документ данных по организациям

            // Настройка шрифта для отображения русских символов
            BaseFont baseFont = BaseFont.createFont(Objects.requireNonNull(getClass().getClassLoader().getResource(
                    "resources/fonts/times-new-roman.ttf")).getPath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(baseFont);

            // Формирование текста PDF документа
            Paragraph title = new Paragraph("Информация о кредитных организациях", font);
            title.setAlignment(Element.ALIGN_CENTER);

            Chapter chapter = new Chapter(title, 1);
            chapter.setNumberDepth(0);

            for (Object cbrData: cbrDataList) {
                if (cbrData.getClass() == CreditOrgInfo.LIC.class) {
                    continue;
                }

                CreditOrgInfo.CO cbrDataObject = (CreditOrgInfo.CO) cbrData;
                Section section = chapter.addSection(new Paragraph(cbrDataObject.getOrgName(), font));

                if (!cbrDataObject.getBIC().equals("")) {
                    section.add(new Paragraph("BIC код: " + cbrDataObject.getBIC(), font));
                }

                if (!String.valueOf((int)cbrDataObject.getIntCode()).equals("")) {
                    section.add(new Paragraph("Внутренний код: " + String.valueOf((int)cbrDataObject.getIntCode()), font));
                }

                if (!cbrDataObject.getRegNumber().toString().equals("")) {
                    section.add(new Paragraph("Регистрационный номер: " + cbrDataObject.getRegNumber().toString(), font));
                }

                if (!cbrDataObject.getOrgFullName().equals("")) {
                    section.add(new Paragraph("Полное имя организации: " + cbrDataObject.getOrgFullName(), font));
                }

                if (!cbrDataObject.getPhones().equals("")) {
                    section.add(new Paragraph("Телефоны: " + cbrDataObject.getPhones(), font));
                }

                if (!getDateNormal(cbrDataObject.getDateKGRRegistration()).equals("")) {
                    section.add(new Paragraph("Дата регистрации в КГР: " + getDateNormal(cbrDataObject.getDateKGRRegistration()), font));
                }

                if (!getDateNormal(cbrDataObject.getMainDateReg()).equals("")) {
                    section.add(new Paragraph("Дата регистрации: " + getDateNormal(cbrDataObject.getMainDateReg()), font));
                }

                if (!cbrDataObject.getUstavAdr().equals("")) {
                    section.add(new Paragraph("Уставной адрес: " + cbrDataObject.getUstavAdr(), font));
                }

                if (!cbrDataObject.getFactAdr().equals("")) {
                    section.add(new Paragraph("Фактический адрес: " + cbrDataObject.getFactAdr(), font));
                }

                if (!cbrDataObject.getDirector().equals("")) {
                    section.add(new Paragraph("Директор: " + cbrDataObject.getDirector(), font));
                }

                if (!String.valueOf(cbrDataObject.getUstMoney().intValue()).equals("")) {
                    section.add(new Paragraph("Капитал: " + String.valueOf(cbrDataObject.getUstMoney().intValue()), font));
                }

                if (!cbrDataObject.getOrgStatus().equals("")) {
                    section.add(new Paragraph("Статус организации: " + cbrDataObject.getOrgStatus(), font));
                }

                if (!cbrDataObject.getRegCode().toString().equals("")) {
                    section.add(new Paragraph("Регистрационный код: " + cbrDataObject.getRegCode().toString(), font));
                }

                if (!getDateNormal(cbrDataObject.getSSVDate()).equals("")) {
                    section.add(new Paragraph("SSV дата: " + getDateNormal(cbrDataObject.getSSVDate()), font));
                }
            }

            document.add(chapter);
            document.close();

            return filePath;
        } catch (DocumentException | IOException e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Создает файл PDF в директории сервиса
     * @return String - путь до файла PDF
     */
    @Override
    public String createFilePDF() throws IOException {
        try {
            String cbrServicePath = "C:\\ProgramData\\CBRService\\PDF\\";
            File serviceDir = new File(cbrServicePath);

            if (!serviceDir.exists()) {
                if (!serviceDir.mkdirs()) {
                    throw new IOException();
                }
            }

            UUID uuid = UUID.randomUUID();
            String fileName = uuid.toString() + ".pdf";
            File filePDF = new File(cbrServicePath + fileName);
            if (!filePDF.exists()) {
                if (filePDF.createNewFile()) {
                    return filePDF.getAbsolutePath();
                } else {
                    throw new IOException();
                }
            } else {
                return filePDF.getAbsolutePath();
            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Получение строки Base64 из файла
     * @param fileName имя файла
     * @return String
     */
    @Override
    public String getBase64FromPDF(String fileName) throws IOException {
        try {
            File file = new File(fileName);

            return Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     *  Получение стандартной формы записи даты
     * @param date XMLGregorianCalendar
     * @return String
     */
    @Override
    public String getDateNormal(XMLGregorianCalendar date) {
        return Integer.toString(date.getYear()) + '-' + Integer.toString(date.getMonth()) + '-' + Integer.toString(date.getDay());
    }

    /**
     * Очистка папки от всех файлов.
     * @param filePath путь до удаляемого файла
     */
    @Override
    public void deleteFile(String filePath) {
        File fileForDelete = new File(filePath);

        if (fileForDelete.isFile() &&  fileForDelete.exists()) {
            fileForDelete.delete();
        }
    }
}
