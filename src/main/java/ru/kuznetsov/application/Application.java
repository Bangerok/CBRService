package ru.kuznetsov.application;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import ru.kuznetsov.service.CBRService;
import ru.kuznetsov.service.CBRServiceImpl;

import java.util.logging.Logger;

public class Application {
    private static Logger log = Logger.getLogger(Application.class.getName());
    private static int PRETTY_PRINT_INDENT_FACTOR = 3;
    private static String TEST_XML_STRING =
            "<?xml version=\"10\" ?><BIC>1234567</BIC><BIC>2345678</BIC><BIC>3456789</BIC>";
    private static String TEST_JSON_STRING =
            "{\"BIC\":[\"1234567\",\"2345678\",\"3456789\"]}";

    public static void main(String[] args){
        CBRService service = new CBRServiceImpl();

        try {
            JSONObject xmlJSONObj = XML.toJSONObject(TEST_XML_STRING);
            JSONObject xmlJSONObj1 = new JSONObject(TEST_JSON_STRING);
            System.out.println(xmlJSONObj.toString());
            System.out.println(xmlJSONObj1.toString());
        } catch (JSONException je) {
            System.out.println(je.toString());
        }

        /*List cosAndLics = service.getCreditOrgInfoList();

        double m = 0;
        assert cosAndLics != null;
        for (Object cosAndLic : cosAndLics) {
            CreditOrgInfo.CO co = (CreditOrgInfo.CO) cosAndLic;
            m = co.getIntCode();
        }

        log.log(Level.INFO, "getCreditOrgInfo returned intCode - " + Double.toString(m));*/
    }
}