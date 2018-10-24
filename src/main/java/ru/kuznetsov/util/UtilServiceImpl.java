package ru.kuznetsov.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.corba.se.spi.orb.ParserDataFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ru.cbr.CreditOrgInfo;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.*;
import java.util.List;

@Service
public class UtilServiceImpl implements UtilService{
    /**
     * Получение списка значений из конечных узлов 3 уровня из строки формата XML
     * @param stringXML формат строки - XML
     * @return ArrayOfDouble
     */
    @Override
    public List getDataFromXML(String stringXML) {
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
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace(System.out);

            return null;
        }
    }

    /**
     * Создание PDF файла из данных организаций, полученных с CBR.ru
     * @param cbrDataList список данных по организациям
     * @return ParserDataFactory
     */
    @Override
    public String createPDFFileFromCBRdata(List cbrDataList) throws IOException {
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
                font.setStyle(Font.ITALIC);
                font.setSize(14);

                if (cbrData.getClass() == CreditOrgInfo.LIC.class) {
                    continue;
                }
                CreditOrgInfo.CO cbrDataObject = (CreditOrgInfo.CO) cbrData;
                Paragraph title11 = new Paragraph(cbrDataObject.getOrgName(), font);
                Section section = chapter.addSection(title11);

                font.setStyle(Font.NORMAL);
                font.setSize(12);
                section.add(new Paragraph("BIC код: " + cbrDataObject.getBIC(), font));
                section.add(new Paragraph("Внутренний код: " + Integer.toString((int)cbrDataObject.getIntCode()), font));
                section.add(new Paragraph("Регистрационный номер: " + cbrDataObject.getRegNumber(), font));
                section.add(new Paragraph("Полное имя организации: " + cbrDataObject.getOrgFullName(), font));
                section.add(new Paragraph("Телефоны: " + cbrDataObject.getPhones(), font));
                section.add(new Paragraph("Дата регистрации в КГР: " + cbrDataObject.getDateKGRRegistration().toString(), font));
                section.add(new Paragraph("Код регистрации: " + cbrDataObject.getMainRegNumber(), font));
                section.add(new Paragraph("Дата регистрации: " + cbrDataObject.getMainDateReg().toString(), font));
                section.add(new Paragraph("Уставной адрес: " + cbrDataObject.getUstavAdr(), font));
                section.add(new Paragraph("Фактический адрес: " + cbrDataObject.getFactAdr(), font));
                section.add(new Paragraph("Директор: " + cbrDataObject.getDirector(), font));
                section.add(new Paragraph("Капитал: " + cbrDataObject.getUstMoney(), font));
                section.add(new Paragraph("Статус организации: " + cbrDataObject.getOrgStatus(), font));
                section.add(new Paragraph("Регистрационный код: " + cbrDataObject.getRegCode(), font));
                section.add(new Paragraph("SSV дата: " + cbrDataObject.getSSVDate(), font));
            }

            document.add(chapter);
            document.close();

            return filePath;
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Создает файл PDF в директории сервиса
     * @return String - путь до файла PDF
     */
    @Override
    public String createFilePDF() {
        try {
            String cbrServicePath = "C:\\ProgramData\\CBRService\\";
            File serviceDir = new File(cbrServicePath);

            if (!serviceDir.exists()) {
                if (!serviceDir.mkdirs()) {
                    throw new IOException();
                }
            }

            UUID uuid = UUID.randomUUID();
            String fileName = uuid.toString() + ".pdf";
            File filePDF = new File("C:\\ProgramData\\CBRService\\" + fileName);
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
            e.printStackTrace();

            return null;
        }
    }

    /**
     * Получение Base64 из файла PDF
     * @param filePDF файл формата PDF
     * @return Base64
     */
    @Override
    public Base64 getBase64FromPDF(ParserDataFactory filePDF) {
        return null;
    }
}
