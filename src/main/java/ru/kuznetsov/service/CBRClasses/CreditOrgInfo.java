
package ru.kuznetsov.service.CBRClasses;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * Справочник по кредитным организациям, ver - 07.03.2017
 * 
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.10
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "CreditOrgInfo", targetNamespace = "http://web.cbr.ru/", wsdlLocation = "file:/C:/Users/kuznetsov/IdeaProjects/CBRService/src/main/resources/WSDL/CreditOrgInfo.WSDL")
public class CreditOrgInfo
    extends Service
{

    private final static URL CREDITORGINFO_WSDL_LOCATION;
    private final static WebServiceException CREDITORGINFO_EXCEPTION;
    private final static QName CREDITORGINFO_QNAME = new QName("http://web.cbr.ru/", "CreditOrgInfo");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/C:/Users/kuznetsov/IdeaProjects/CBRService/src/main/resources/WSDL/CreditOrgInfo.WSDL");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CREDITORGINFO_WSDL_LOCATION = url;
        CREDITORGINFO_EXCEPTION = e;
    }

    public CreditOrgInfo() {
        super(__getWsdlLocation(), CREDITORGINFO_QNAME);
    }

    public CreditOrgInfo(WebServiceFeature... features) {
        super(__getWsdlLocation(), CREDITORGINFO_QNAME, features);
    }

    public CreditOrgInfo(URL wsdlLocation) {
        super(wsdlLocation, CREDITORGINFO_QNAME);
    }

    public CreditOrgInfo(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CREDITORGINFO_QNAME, features);
    }

    public CreditOrgInfo(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CreditOrgInfo(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns CreditOrgInfoSoap
     */
    @WebEndpoint(name = "CreditOrgInfoSoap")
    public CreditOrgInfoSoap getCreditOrgInfoSoap() {
        return super.getPort(new QName("http://web.cbr.ru/", "CreditOrgInfoSoap"), CreditOrgInfoSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CreditOrgInfoSoap
     */
    @WebEndpoint(name = "CreditOrgInfoSoap")
    public CreditOrgInfoSoap getCreditOrgInfoSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://web.cbr.ru/", "CreditOrgInfoSoap"), CreditOrgInfoSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CREDITORGINFO_EXCEPTION!= null) {
            throw CREDITORGINFO_EXCEPTION;
        }
        return CREDITORGINFO_WSDL_LOCATION;
    }

}
