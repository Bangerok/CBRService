package ru.kuznetsov.application;

import ru.cbr.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {
    private static Logger log = Logger.getLogger(Application.class.getName());
    private CreditOrgInfoSoap soap;

    public Application() {
        this.soap = getCreditOrgInfoSoap();
    }
    public static void main(String[] args){
        Application app = new Application();
        CreditInfoByIntCodeExXMLResponse.CreditInfoByIntCodeExXMLResult result = app.getCreditOrgInfoElement();

        assert result != null;
        List cosAndLics = ((CreditOrgInfo) result.getContent().get(0)).getCOSAndLICS();

        double m = 0;
        for (Object cosAndLic : cosAndLics) {
            CreditOrgInfo.CO co = (CreditOrgInfo.CO) cosAndLic;
            m = co.getIntCode();
        }

        log.log(Level.INFO, "getCreditOrgInfo returned intCode - " + Double.toString(m));
    }

    private CreditInfoByIntCodeExXMLResponse.CreditInfoByIntCodeExXMLResult getCreditOrgInfoElement(){
        try {
            double internalCode = soap.bicToIntCode("040173725");
            log.log(Level.INFO, "bicToIntCode returned internal code - " + Double.toString(internalCode));

            ArrayOfDouble internalCodes = new ArrayOfDouble();
            internalCodes.getDoubles().add(internalCode);

            CreditInfoByIntCodeExXMLResponse.CreditInfoByIntCodeExXMLResult info;
            info = soap.creditInfoByIntCodeExXML(internalCodes);

            return info;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Exception - " + e.getMessage());
            return null;
        }
    }

    /*private CreditOrgInfoSoap getCreditOrgInfo(){
        try {
            return null;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Exception - " + e.getMessage());
            return null;
        }
    }*/

    private CreditOrgInfoSoap getCreditOrgInfoSoap(){
        try {
            return new CreditOrgInfo_Service().getCreditOrgInfoSoap();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Exception [getCreditOrgInfoSoap] - " + e.getMessage());
            return null;
        }
    }
}