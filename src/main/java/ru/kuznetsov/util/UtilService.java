package ru.kuznetsov.util;

import com.sun.corba.se.spi.orb.ParserDataFactory;

import java.util.Base64;
import java.util.List;

public interface UtilService {
    List getDataFromXML(String XML);
    ParserDataFactory createPDFFileFromCBRdata(List cbrDataList);
    Base64 getBase64FromPDF(ParserDataFactory filePDF);
}
