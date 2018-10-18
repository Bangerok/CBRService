package ru.kuznetsov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class Application {
    private static Logger log = Logger.getLogger(Application.class.getName());

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);

        /*CBRService service = new CBRServiceImpl();

        List cosAndLics = service.getCreditOrgInfoList();

        double m = 0;
        assert cosAndLics != null;
        for (Object cosAndLic : cosAndLics) {
            CreditOrgInfo.CO co = (CreditOrgInfo.CO) cosAndLic;
            m = co.getIntCode();
        }

        log.log(Level.INFO, "getCreditOrgInfo returned intCode - " + Double.toString(m));*/
    }
}