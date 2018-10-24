package ru.kuznetsov.util;

import com.sun.corba.se.spi.orb.ParserDataFactory;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

public interface UtilService {
    List getDataFromXML(String XML);
    String createFilePDF();
    String createPDFFileFromCBRdata(List cbrDataList) throws IOException;
    Base64 getBase64FromPDF(ParserDataFactory filePDF);
}
