package ru.kuznetsov.util;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.util.List;

public interface UtilService {
    List getDataFromXML(String XML) throws Exception;
    String createFilePDF() throws IOException;
    String createPDFFileFromCBRdata(List cbrDataList) throws Exception;
    String getDateNormal(XMLGregorianCalendar date);
    String getBase64FromPDF(String fileName) throws IOException;
    void deleteFile(String filePath);
}
