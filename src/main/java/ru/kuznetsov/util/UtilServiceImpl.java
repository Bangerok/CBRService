package ru.kuznetsov.util;

import com.sun.corba.se.spi.orb.ParserDataFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Base64;
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
    public ParserDataFactory createPDFFileFromCBRdata(List cbrDataList) {
        return null;
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
