package ru.kuznetsov.util;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.util.List;

public interface UtilService {
    List getDataFromXML(String XML);
    String createFilePDF();
    String createPDFFileFromCBRdata(List cbrDataList);
    String getDateNormal(XMLGregorianCalendar date);
    String getBase64FromPDF(String fileName) throws IOException;
}
